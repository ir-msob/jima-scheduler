package ir.msob.jima.scheduler.commons.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleSchedule extends Schedule {
}
