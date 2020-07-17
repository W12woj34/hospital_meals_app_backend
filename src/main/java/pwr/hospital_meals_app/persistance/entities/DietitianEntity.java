package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;


@Entity
@Table(name = "dietitian")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietitianEntity extends EmployeeEntity {

    @OneToMany(mappedBy = "dietitian", fetch = FetchType.LAZY)
    private Collection<DietaryRestrictionEntity> dietaryRestrictions;
}
