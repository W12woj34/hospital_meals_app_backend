package pwr.hospital_meals_app.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "ward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardEntity extends AbstractPersistable<Integer> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "ward", fetch = FetchType.LAZY)
    private Collection<WardNurseEntity> nurses;

    @OneToMany(mappedBy = "ward", fetch = FetchType.LAZY)
    private Collection<StayEntity> stays;
}
