package pwr.hospital_meals_app.dto.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import pwr.hospital_meals_app.dto.groups.OnCreate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonPropertyOrder({"id"})
public abstract class BaseInheritedIdDto<ID extends Serializable> implements PersistableDto<ID> {

    @NotNull(groups = {OnCreate.class})
    private ID id;
}
