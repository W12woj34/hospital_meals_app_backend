package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity extends PersonEntity {


    @Column(name = "additional_info", nullable = true)
    private String additionalInfo;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<PatientDietEntity> patientDiets;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<StayEntity> stays;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<DietaryRestrictionEntity> dietaryRestrictions;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<OrderEntity> orders;



}
