package ir.msob.jima.scheduler.api.kafka.service;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.core.api.kafka.commons.BaseKafkaListener;
import ir.msob.jima.core.commons.listener.BaseListener;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.scheduler.api.kafka.client.Constants;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import ir.msob.jima.scheduler.service.BaseSchedulerService;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.kafka.listener.ContainerProperties;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface ParentSchedulerListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        J extends BaseJob,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends BaseKafkaListener<ID, USER> {

    default Class<J> getJobClass() {
        return (Class<J>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentSchedulerListener.class, 2);
    }

    default ContainerProperties createContainerProperties(String operation) {
        return createKafkaContainerProperties(Constants.getChannel(getJobClass(), operation));
    }

    default void startContainer(ContainerProperties containerProperties, String operation) {
        startKafkaContainer(containerProperties, Constants.getChannel(getJobClass(), operation));
    }

    S getService();

    @SneakyThrows
    default void sendCallbackDtos(ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> message, Collection<JobDto> dtos, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtosMessage<String, JobDto> data = new DtosMessage<>();
            data.setDtos(dtos);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackDto(ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> message, JobDto dto, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtoMessage<String, JobDto> data = new DtoMessage<>();
            data.setDto(dto);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackBoolean(ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> message, Boolean result, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            BooleanMessage data = new BooleanMessage();
            data.setResult(result);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackDtoFromJobDtoMessage(ChannelMessage<ID, USER, DtoMessage<String, JobDto>> message, JobDto dto, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtoMessage<String, JobDto> data = new DtoMessage<>();
            data.setDto(dto);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackIds(ChannelMessage<ID, USER, DtosMessage<String, JobDto>> message, Collection<String> ids, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            IdsMessage<String> data = new IdsMessage<>();
            data.setIds(ids);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackId(ChannelMessage<ID, USER, DtosMessage<String, JobDto>> message, String id, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            IdMessage<String> data = new IdMessage<>();
            data.setId(id);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackCount(ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> message, Long count, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackPage(ChannelMessage<ID, USER, PageableMessage<String, JobCriteria>> message, Page<JobDto> page, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            PageMessage<String, JobDto> data = new PageMessage<>();
            data.setPage(page);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    TypeReference<ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>>> getCriteriaReferenceType();

    TypeReference<ChannelMessage<ID, USER, PageableMessage<String, JobCriteria>>> getCriteriaPageReferenceType();

    TypeReference<ChannelMessage<ID, USER, DtoMessage<String, JobDto>>> getDtoReferenceType();
}
