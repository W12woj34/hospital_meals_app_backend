package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends AbstractPersistable<Integer> {


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private MealEntity meal;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private WardNurseEntity nurse;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatusEntity status;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;


}
