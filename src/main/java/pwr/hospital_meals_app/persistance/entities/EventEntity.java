package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends AbstractPersistable<Integer> {

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private Collection<LogEntity> logs;

}
