package pwr.hospital_meals_app.persistance.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "restriction_status")
@Data
@NoArgsConstructor
public class RestrictionStatusEntity extends AbstractPersistable<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private Collection<DietaryRestrictionEntity> dietaryRestrictions;


}
