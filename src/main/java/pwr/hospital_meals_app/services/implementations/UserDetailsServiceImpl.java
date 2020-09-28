package pwr.hospital_meals_app.services.implementations;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pwr.hospital_meals_app.persistance.entities.LoginEntity;
import pwr.hospital_meals_app.persistance.repositories.*;

import java.util.Collections;
import java.util.Objects;

import static pwr.hospital_meals_app.security.SecurityConstants.*;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final WardNurseRepository wardNurseRepository;
    private final DietitianRepository dietitianRepository;
    private final MainKitchenDietitianRepository mainKitchenDietitianRepository;
    private final PatientMovementRepository patientMovementRepository;


    public UserDetailsServiceImpl(
            LoginRepository loginRepository,
            WardNurseRepository wardNurseRepository,
            DietitianRepository dietitianRepository,
            MainKitchenDietitianRepository mainKitchenDietitianRepository,
            PatientMovementRepository patientMovementRepository) {

        this.loginRepository = loginRepository;
        this.wardNurseRepository = wardNurseRepository;
        this.dietitianRepository = dietitianRepository;
        this.mainKitchenDietitianRepository = mainKitchenDietitianRepository;
        this.patientMovementRepository = patientMovementRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginEntity user = loginRepository.findByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        if (wardNurseRepository.findById(Objects.requireNonNull(user.getId())).isPresent()) {
            return new User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_NURSE)));
        }

        if (dietitianRepository.findById(Objects.requireNonNull(user.getId())).isPresent()) {
            return new User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_DIETITIAN)));
        }

        if (mainKitchenDietitianRepository.findById(Objects.requireNonNull(user.getId())).isPresent()) {
            return new User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_KITCHEN)));
        }

        if (patientMovementRepository.findById(Objects.requireNonNull(user.getId())).isPresent()) {
            return new User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + ROLE_MOVEMENT)));
        }

        throw new UsernameNotFoundException(username);
    }

}
