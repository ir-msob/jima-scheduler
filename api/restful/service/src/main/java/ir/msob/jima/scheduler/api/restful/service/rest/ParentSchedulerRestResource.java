package ir.msob.jima.scheduler.api.restful.service.rest;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.service.BaseSchedulerService;

import java.io.Serializable;

public interface ParentSchedulerRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends BaseRestResource<ID, USER> {

    S getService();
}
