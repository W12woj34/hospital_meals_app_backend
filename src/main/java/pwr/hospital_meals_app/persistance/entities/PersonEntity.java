package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity extends AbstractPersistable<Integer> {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<LogEntity> logsUser;

    @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
    private Collection<LogEntity> logsTarget;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private PatientEntity patient;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private EmployeeEntity employee;

}
