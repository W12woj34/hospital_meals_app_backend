package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "meal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealEntity extends AbstractPersistable<Integer> {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private OrderEntity order;

    @Column(name = "additional_info", nullable = true)
    private String additionalInfo;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private MealTypeEntity type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id")
    private DietEntity diet;

}
