package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity extends AbstractPersistable<Integer> {

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "modified_entity_id", nullable = false)
    private int modifiedEntityId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EmployeeEntity user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

}
