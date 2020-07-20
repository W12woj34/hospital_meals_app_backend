package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ward_nurse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardNurseEntity extends EmployeeEntity {

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private WardEntity ward;

    @OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY)
    private Collection<OrderEntity> orders;


}
