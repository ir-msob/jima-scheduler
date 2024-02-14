package ir.msob.jima.scheduler.service;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface BaseSchedulerService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, R extends BaseSchedulerRepository> {

    R getRepository();

    @MethodStats
    default <J extends BaseJob> Mono<JobDto> save(JobDto dto, Class<J> jobClass, Optional<USER> user) {
        return getRepository().save(dto, jobClass);
    }

    @MethodStats
    default Mono<Boolean> delete(JobCriteria criteria, Optional<USER> user) {
        return getRepository().delete(criteria);
    }

    @MethodStats
    default Mono<Boolean> suspend(JobCriteria criteria, Optional<USER> user) {
        return getRepository().suspend(criteria);
    }

    @MethodStats
    default Mono<Boolean> resume(JobCriteria criteria, Optional<USER> user) {
        return getRepository().resume(criteria);
    }

    @MethodStats
    default Mono<JobDto> getOne(JobCriteria criteria, Optional<USER> user) {
        return getRepository().getOne(criteria);
    }

    @MethodStats
    default Mono<Collection<JobDto>> getMany(JobCriteria criteria, Optional<USER> user) {
        return getRepository().getMany(criteria);
    }

    @MethodStats
    default Mono<Page<JobDto>> getPage(JobCriteria criteria, Pageable pageable, Optional<USER> user) {
        return getRepository().getPage(criteria, pageable);
    }
}
