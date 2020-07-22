package pwr.hospital_meals_app.dto.base;

import java.io.Serializable;

public interface PersistableDto<ID extends Serializable> {

    ID getId();

    void setId(ID id);
}
