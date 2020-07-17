package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@Table(name = "dietary_restriction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaryRestrictionEntity extends AbstractPersistable<Integer> {

    @Column(name = "restriction", nullable = false)
    private String restriction;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "dietitian_id")
    private DietitianEntity dietitian;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RestrictionStatusEntity status;


}
