package ir.msob.jima.scheduler.commons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface BaseSchedulerRepository {

    <J extends BaseJob> Mono<JobDto> save(JobDto dto, Class<J> jobClass);

    Mono<Boolean> delete(JobCriteria criteria);

    Mono<Boolean> suspend(JobCriteria criteria);

    Mono<Boolean> resume(JobCriteria criteria);

    Mono<JobDto> getOne(JobCriteria criteria);

    Mono<Collection<JobDto>> getMany(JobCriteria criteria);

    Mono<Page<JobDto>> getPage(JobCriteria criteria, Pageable pageable);
}
