package ir.msob.jima.scheduler.client;

import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseSchedulerClient {
    <J extends BaseJob> Mono<JobDto> save(JobDto dto, Class<J> jobClass);

    Mono<Boolean> delete(JobCriteria criteria);

    Mono<Boolean> suspend(JobCriteria criteria);

    Mono<Boolean> resume(JobCriteria criteria);

    Mono<JobDto> getOne(JobCriteria criteria);

    Flux<JobDto> getMany(JobCriteria criteria);

    Mono<Page<JobDto>> getPage(JobCriteria criteria, Pageable pageable);
}
