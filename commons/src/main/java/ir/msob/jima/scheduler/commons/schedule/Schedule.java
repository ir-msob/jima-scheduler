package ir.msob.jima.scheduler.commons.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "scheduleType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleSchedule.class, name = "simple"),
        @JsonSubTypes.Type(value = CronSchedule.class, name = "cron")
})
public abstract class Schedule {
    private String scheduleType;
    private Instant startAt;
}
