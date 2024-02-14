package ir.msob.jima.scheduler.api.restful.service.rest;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.service.BaseSchedulerService;

import java.io.Serializable;

public interface BaseSchedulerRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        J extends BaseJob,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends
        BaseDeleteSchedulerRestResource<ID, USER, R, S>,
        BaseSaveSchedulerRestResource<ID, USER, J, R, S>,
        BaseGetManySchedulerRestResource<ID, USER, R, S>,
        BaseGetPageSchedulerRestResource<ID, USER, R, S>,
        BaseGetOneSchedulerRestResource<ID, USER, R, S>,
        BaseResumeSchedulerRestResource<ID, USER, R, S>,
        BaseSuspendSchedulerRestResource<ID, USER, R, S> {

}
