package pwr.hospital_meals_app.persistance.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "order_status")
@Data
@NoArgsConstructor
public class OrderStatusEntity extends AbstractPersistable<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private Collection<OrderEntity> orders;

}
