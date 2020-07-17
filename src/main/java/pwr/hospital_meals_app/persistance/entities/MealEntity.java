package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "meal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealEntity extends BaseInheritedIdEntity<Integer> {

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private OrderEntity order;

    @Column(name = "additional_info", nullable = true)
    private String additionalInfo;

    @Column(name = "option", nullable = false)
    private int option;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private MealTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "diet_id")
    private DietEntity diet;

}
