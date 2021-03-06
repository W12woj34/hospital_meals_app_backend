package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ward_nurse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardNurseEntity extends EmployeeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private WardEntity ward;


}
