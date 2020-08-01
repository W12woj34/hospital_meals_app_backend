package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayEntity extends AbstractPersistable<Integer> {

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "release_date", nullable = true)
    private LocalDate releaseDate;

    @Column(name = "archived", nullable = false)
    private boolean archived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private WardEntity ward;
}
