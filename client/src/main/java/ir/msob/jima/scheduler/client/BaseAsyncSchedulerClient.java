package ir.msob.jima.scheduler.client;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public interface BaseAsyncSchedulerClient {
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void getOne(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void getMany(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void getPage(JobCriteria criteria, Pageable pageable, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);


    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void save(JobDto dto, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void delete(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void suspend(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void resume(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user);
}
