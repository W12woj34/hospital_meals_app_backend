package pwr.hospital_meals_app.persistance.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "employee")
@Data
@NoArgsConstructor
public class EmployeeEntity extends PersonEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private LoginEntity login;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<LogEntity> logs;
}
