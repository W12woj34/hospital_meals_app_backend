package pwr.hospital_meals_app.persistance.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "dietitian")
@Data
@NoArgsConstructor
public class DietitianEntity extends EmployeeEntity {

}
