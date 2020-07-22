package pwr.hospital_meals_app.dto.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import pwr.hospital_meals_app.groups.OnCreate;

import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@JsonPropertyOrder({"id"})
public abstract class BaseGeneratedIdDto<ID extends Serializable> implements PersistableDto<ID> {

    @Null(groups = {OnCreate.class})
    private ID id;
}
