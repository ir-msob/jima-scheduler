package ir.msob.jima.scheduler.api.kafka.service;

import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.api.kafka.service.read.BaseGetManySchedulerListener;
import ir.msob.jima.scheduler.api.kafka.service.read.BaseGetOneSchedulerListener;
import ir.msob.jima.scheduler.api.kafka.service.read.BaseGetPageSchedulerListener;
import ir.msob.jima.scheduler.api.kafka.service.write.BaseDeleteSchedulerListener;
import ir.msob.jima.scheduler.api.kafka.service.write.BaseResumeSchedulerListener;
import ir.msob.jima.scheduler.api.kafka.service.write.BaseSaveSchedulerListener;
import ir.msob.jima.scheduler.api.kafka.service.write.BaseSuspendSchedulerListener;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.service.BaseSchedulerService;

import java.io.Serializable;

public interface BaseSchedulerListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        J extends BaseJob,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends
        BaseGetManySchedulerListener<ID, USER, J, R, S>,
        BaseGetOneSchedulerListener<ID, USER, J, R, S>,
        BaseGetPageSchedulerListener<ID, USER, J, R, S>,
        BaseSaveSchedulerListener<ID, USER, J, R, S>,
        BaseDeleteSchedulerListener<ID, USER, J, R, S>,
        BaseSuspendSchedulerListener<ID, USER, J, R, S>,
        BaseResumeSchedulerListener<ID, USER, J, R, S> {

}
