package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "diet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietEntity extends AbstractPersistable<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "diet", fetch = FetchType.LAZY)
    private Collection<MealEntity> meals;

    @OneToMany(mappedBy = "diet", fetch = FetchType.LAZY)
    private Collection<PatientDietEntity> patientsDiets;

}
