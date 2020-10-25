package pwr.hospital_meals_app.services.implementations;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.dto.additionals.MealDemandsDto;
import pwr.hospital_meals_app.dto.reports.DietMealsDto;
import pwr.hospital_meals_app.dto.reports.PatientsAndMealsDto;
import pwr.hospital_meals_app.dto.reports.SummaryMealsDto;
import pwr.hospital_meals_app.dto.reports.WardAndDietsDto;
import pwr.hospital_meals_app.persistance.entities.*;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.ReportService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final MealRepository mealRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final StayRepository stayRepository;
    private final DietRepository dietRepository;
    private final WardRepository wardRepository;

    public ReportServiceImpl(OrderRepository orderRepository,
                             MealRepository mealRepository, OrderStatusRepository orderStatusRepository,
                             StayRepository stayRepository,
                             DietRepository dietRepository,
                             WardRepository wardRepository) {
        this.orderRepository = orderRepository;
        this.mealRepository = mealRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.stayRepository = stayRepository;
        this.dietRepository = dietRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public Resource generatePatientsAndMealsReport(LocalDate date) {

        List<PatientsAndMealsDto> dtos = new ArrayList<>();
        List<WardEntity> wards = wardRepository.findAll();

        wards.sort(Comparator.comparing(WardEntity::getId));
        for (WardEntity ward : wards) {
            PatientsAndMealsDto dto = new PatientsAndMealsDto();

            dto.setWardId(ward.getId());
            dto.setWardName(ward.getName());


            List<StayEntity> stays =
                    stayRepository.findByWard(ward).stream()
                            .filter(s -> s.getAdmissionDate().compareTo(date) <= 0
                                    && (s.getReleaseDate() == null || s.getReleaseDate().compareTo(date) >= 0))
                            .collect(Collectors.toList());

            dto.setPatients(stays.size());

            List<MealTypeEntity> mealTypes = stays
                    .stream()
                    .map(StayEntity::getPatient)
                    .map(PatientEntity::getOrders)
                    .flatMap(Collection::stream)
                    .map(OrderEntity::getMeal)
                    .filter(m -> m.getDate() == date)
                    .map(MealEntity::getType)
                    .collect(Collectors.toList());

            int breakfast = 0;
            int lunch = 0;
            int supper = 0;

            for (MealTypeEntity mealType : mealTypes) {
                if (mealType.getName().equals("Śniadanie")) {
                    breakfast += 1;
                } else if (mealType.getName().equals("Obiad")) {
                    lunch += 1;
                } else {
                    supper += 1;
                }
            }

            dto.setBreakfasts(breakfast);
            dto.setLunches(lunch);
            dto.setSuppers(supper);

            dtos.add(dto);
        }


        JRBeanCollectionDataSource dtosJRBean = new JRBeanCollectionDataSource(dtos);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("DtosDataSource", dtosJRBean);
        parameters.put("reportDate", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Resource resource = null;
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager
                    .fillReport("src\\main\\resources\\reports\\PatientsAndMealsReport.jasper",
                            parameters,
                            new JREmptyDataSource());

            resource = new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));

        } catch (JRException e) {
            e.printStackTrace();
        }

        return resource;

    }

    @Override
    public Resource generateSummaryMealsReport() {

        List<SummaryMealsDto> dtos = new ArrayList<>();
        List<WardEntity> wards = wardRepository.findAll();

        wards.sort(Comparator.comparing(WardEntity::getId));
        for (WardEntity ward : wards) {
            SummaryMealsDto dto = new SummaryMealsDto();

            dto.setWardId(ward.getId());
            dto.setWardName(ward.getName());

            List<DietEntity> diets = stayRepository
                    .findByArchivedAndWard(false, ward)
                    .stream()
                    .map(StayEntity::getPatient)
                    .map(PatientEntity::getPatientDiets)
                    .flatMap(Collection::stream)
                    .filter(pd -> pd.getEndDate() == null)
                    .map(PatientDietEntity::getDiet)
                    .collect(Collectors.toList());

            int strict = 0;
            int lightAndOthers = 0;
            int basic = 0;

            for (DietEntity diet : diets) {
                if (diet.getName().equals("Ścisła")) {
                    strict += 1;
                } else if (diet.getName().equals("Podstawowa")) {
                    basic += 1;
                } else {
                    lightAndOthers += 1;
                }
            }

            dto.setStrict(strict);
            dto.setLightAndOthers(lightAndOthers);
            dto.setBasic(basic);

            dtos.add(dto);
        }

        JRBeanCollectionDataSource dtosJRBean = new JRBeanCollectionDataSource(dtos);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("DtosDataSource", dtosJRBean);

        Resource resource = null;

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager
                    .fillReport("src\\main\\resources\\reports\\SummaryMealsReport.jasper",
                            parameters,
                            new JREmptyDataSource());

            resource = new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));
        } catch (JRException e) {
            e.printStackTrace();
        }

        return resource;
    }

    @Override
    public Resource generateTodayMealsDemand() {

        List<WardAndDietsDto> dtos = new ArrayList<>();
        List<WardEntity> wards = wardRepository.findAll();
        wards.sort(Comparator.comparing(WardEntity::getId));

        for (WardEntity ward : wards) {
            WardAndDietsDto dto = new WardAndDietsDto();

            dto.setWardId(ward.getId());
            dto.setWardName(ward.getName());


            List<MealEntity> patientsMeals = stayRepository
                    .findByArchivedAndWard(false, ward)
                    .stream()
                    .map(StayEntity::getPatient)
                    .map(PatientEntity::getOrders)
                    .flatMap(Collection::stream)
                    .filter(o -> !o.getStatus().getName()
                            .equals("Anulowane"))
                    .map(OrderEntity::getMeal)
                    .filter(m -> m.getDate()
                            .equals(LocalDate.now()))
                    .collect(Collectors.toList());


            List<OrderEntity> patientsOrders = patientsMeals.stream()
                    .map(MealEntity::getOrder)
                    .collect(Collectors.toList());

            for (OrderEntity order : patientsOrders) {
                if (!order.getStatus().getName().equals("Przyjęte")) {
                    order.setStatus(orderStatusRepository.findById(2)
                            .orElseThrow(EntityNotFoundException::new));
                    orderRepository.save(order);
                }

            }

            List<DietMealsDto> dietList = new ArrayList<>();

            List<DietEntity> diets = dietRepository.findAll();
            diets.sort(Comparator.comparing(DietEntity::getId));
            for (DietEntity diet : diets) {

                DietMealsDto dietDto = new DietMealsDto();

                dietDto.setDietName(diet.getName());

                int breakfasts = (int) patientsMeals.stream()
                        .filter(pm -> pm.getType().getName().equals("Śniadanie") &&
                                pm.getDiet().getName().equals(diet.getName()))
                        .count();

                int lunches = (int) patientsMeals.stream()
                        .filter(pm -> pm.getType().getName().equals("Obiad") &&
                                pm.getDiet().getName().equals(diet.getName()))
                        .count();

                int suppers = (int) patientsMeals.stream()
                        .filter(pm -> pm.getType().getName().equals("Kolacja") &&
                                pm.getDiet().getName().equals(diet.getName()))
                        .count();


                dietDto.setBreakfasts(breakfasts);
                dietDto.setLunches(lunches);
                dietDto.setSuppers(suppers);

                dietList.add(dietDto);
            }

            dto.setDietMealsList(dietList);
            dtos.add(dto);
        }

        JRBeanCollectionDataSource dtosJRBean = new JRBeanCollectionDataSource(dtos);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("DtosDataSource", dtosJRBean);

        Resource resource = null;

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager
                    .fillReport("src\\main\\resources\\reports\\WardsAndMealsReport.jasper",
                            parameters,
                            new JREmptyDataSource());

            resource = new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));
        } catch (JRException e) {
            e.printStackTrace();
        }

        return resource;
    }

    @Override
    public MealDemandsDto getMealDemands() {

        MealDemandsDto dto = new MealDemandsDto();

        List<OrderEntity> todayOrders = mealRepository.findByDate(LocalDate.now()).stream()
                .map(MealEntity::getOrder).collect(Collectors.toList());

        List<String> timeSignatures = todayOrders.stream()
                .filter(o -> !o.getStatus().getName().equals("Przyjęte"))
                .map(OrderEntity::getTimestamp)
                .sorted().collect(Collectors.toList());

        if (timeSignatures.isEmpty()) {
            dto.setLastUpdate("");
        } else {
            dto.setLastUpdate(timeSignatures.get(timeSignatures.size() - 1));
        }

        List<MealEntity> todayMeals = todayOrders.stream()
                .filter(o -> !o.getStatus().getName().equals("Anulowane"))
                .map(OrderEntity::getMeal).collect(Collectors.toList());

        int breakfasts = (int) todayMeals.stream()
                .filter(pm -> pm.getType().getName().equals("Śniadanie"))
                .count();

        int lunches = (int) todayMeals.stream()
                .filter(pm -> pm.getType().getName().equals("Obiad"))
                .count();

        int suppers = (int) todayMeals.stream()
                .filter(pm -> pm.getType().getName().equals("Kolacja"))
                .count();

        dto.setBreakfasts(breakfasts);
        dto.setLunches(lunches);
        dto.setSuppers(suppers);

        return dto;
    }
}
