package pwr.hospital_meals_app.services.implementations;

import pwr.hospital_meals_app.dto.reports.PatientsAndMealsDto;
import pwr.hospital_meals_app.dto.reports.SummaryMealsDto;
import pwr.hospital_meals_app.persistance.entities.*;
import pwr.hospital_meals_app.persistance.repositories.*;
import pwr.hospital_meals_app.services.definitions.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final StayRepository stayRepository;
    private final MealRepository mealRepository;
    private final DietRepository dietRepository;
    private final PatientDietRepository patientDietRepository;
    private final PatientRepository patientRepository;
    private final WardRepository wardRepository;
    private final MealTypeRepository mealTypeRepository;

    public ReportServiceImpl(OrderRepository orderRepository,
                             StayRepository stayRepository,
                             MealRepository mealRepository,
                             DietRepository dietRepository,
                             PatientDietRepository patientDietRepository,
                             PatientRepository patientRepository,
                             WardRepository wardRepository,
                             MealTypeRepository mealTypeRepository) {
        this.orderRepository = orderRepository;
        this.stayRepository = stayRepository;
        this.mealRepository = mealRepository;
        this.dietRepository = dietRepository;
        this.patientDietRepository = patientDietRepository;
        this.patientRepository = patientRepository;
        this.wardRepository = wardRepository;
        this.mealTypeRepository = mealTypeRepository;
    }

    @Override
    public void generatePatientsAndMealsReport(LocalDate date) {

        List<PatientsAndMealsDto> dtos = new ArrayList<>();
        List<WardEntity> wards = wardRepository.findAll();

        for (WardEntity ward : wards) {
            PatientsAndMealsDto dto = new PatientsAndMealsDto();

            dto.setWardId(ward.getId());
            dto.setWardName(ward.getName());

            List<StayEntity> stays =
                    stayRepository.findByWardAndAdmissionDateLessThanEqualAndReleaseDateGreaterThanEqual(
                            ward,
                            date,
                            date);

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

    }

    @Override
    public void generateSummaryMealsReport() {

        List<SummaryMealsDto> dtos = new ArrayList<>();
        List<WardEntity> wards = wardRepository.findAll();

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


    }
}
