package pwr.hospital_meals_app.persistance.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patient_movement")
@Data
@NoArgsConstructor
public class PatientMovementEntity extends EmployeeEntity {

}
