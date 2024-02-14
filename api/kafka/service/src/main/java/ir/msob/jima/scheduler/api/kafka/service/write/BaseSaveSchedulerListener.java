package ir.msob.jima.scheduler.api.kafka.service.write;

import ir.msob.jima.core.commons.annotation.async.CallbackError;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.api.kafka.service.ParentSchedulerListener;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobDto;
import ir.msob.jima.scheduler.service.BaseSchedulerService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.io.Serializable;
import java.util.Optional;

public interface BaseSaveSchedulerListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        J extends BaseJob,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends ParentSchedulerListener<ID, USER, J, R, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveSchedulerListener.class);

    @PostConstruct
    default void save() {
        String operation = Operations.SAVE;

        ContainerProperties containerProperties = createContainerProperties(operation);
        containerProperties.setMessageListener((MessageListener<String, String>) dto -> serviceSave(dto.value()));
        startContainer(containerProperties, operation);
    }

    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    private void serviceSave(String dto) {
        ChannelMessage<ID, USER, DtoMessage<String, JobDto>> message = getObjectMapper().readValue(dto, getDtoReferenceType());
        Optional<USER> user = Optional.ofNullable(message.getUser());
        getService().save(message.getData().getDto(), getJobClass(), user)
                .subscribe(saveDto -> sendCallbackDtoFromJobDtoMessage(message, saveDto, OperationsStatus.SAVE, user));
    }
}
