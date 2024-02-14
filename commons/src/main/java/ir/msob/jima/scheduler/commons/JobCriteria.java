package ir.msob.jima.scheduler.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.criteria.filter.Filter;
import ir.msob.jima.core.commons.model.dto.ModelType;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobCriteria extends ModelType implements BaseCriteria<String> {
    private Filter<String> group;
    private Filter<String> identity;

    @Override
    public Filter<String> getId() {
        return getIdentity();
    }

    @Override
    public void setId(Filter<String> id) {
        setIdentity(id);
    }
}
