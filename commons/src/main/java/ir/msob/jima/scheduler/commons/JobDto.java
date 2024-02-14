package ir.msob.jima.scheduler.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.scheduler.commons.schedule.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobDto implements BaseDto<String> {
    @NotBlank
    private String group;
    @NotBlank
    private String identity;
    private Map<String, Object> params = new HashMap<>();
    private Schedule schedule;

    @Override
    public String getDomainId() {
        return getIdentity();
    }

    @Override
    public void setDomainId(String id) {
        setIdentity(id);
    }

    @Override
    public String getDomainIdName() {
        return FN.identity.name();
    }

    @Override
    public int compareTo(BaseDomain<String> o) {
        return BaseDto.super.compareTo(o);
    }

    public enum FN {
        group, identity, params, schedule
    }
}
