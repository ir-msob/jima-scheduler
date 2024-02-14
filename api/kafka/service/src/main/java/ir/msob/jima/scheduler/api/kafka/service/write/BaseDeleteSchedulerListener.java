package ir.msob.jima.scheduler.api.kafka.service.write;

import ir.msob.jima.core.commons.annotation.async.CallbackError;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.api.kafka.service.ParentSchedulerListener;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.service.BaseSchedulerService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

public interface BaseDeleteSchedulerListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        J extends BaseJob,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends ParentSchedulerListener<ID, USER, J, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteSchedulerListener.class);

    @PostConstruct
    default void delete() {
        String operation = Operations.DELETE;

        ContainerProperties containerProperties = createContainerProperties(operation);
        containerProperties.setMessageListener((MessageListener<String, String>) dto -> serviceDelete(dto.value()));
        startContainer(containerProperties, operation);
    }

    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    private void serviceDelete(String dto) {
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> message = getObjectMapper().readValue(dto, getCriteriaReferenceType());
        Optional<USER> user = Optional.ofNullable(message.getUser());
        getService().delete(message.getData().getCriteria(), user)
                .subscribe(delete -> sendCallbackBoolean(message, delete, OperationsStatus.DELETE, user));
    }
}
