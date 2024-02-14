package ir.msob.jima.scheduler.api.kafka.client;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.client.BaseAsyncClient;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.channel.message.PageableMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.client.BaseAsyncSchedulerClient;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SchedulerProducer implements BaseAsyncSchedulerClient {

    private final BaseAsyncClient asyncClient;

    public static <J extends BaseJob> String createChannel(Class<J> jobClass, String operation) {
        return Constants.getChannel(jobClass, operation);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void getOne(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<String, JobCriteria> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.GET_ONE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void getMany(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<String, JobCriteria> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.GET_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void getPage(JobCriteria criteria, Pageable pageable, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        PageableMessage<String, JobCriteria> data = createData(criteria, pageable);
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.GET_PAGE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void save(JobDto dto, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        DtoMessage<String, JobDto> data = createMessage(dto);
        ChannelMessage<ID, USER, DtoMessage<String, JobDto>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.SAVE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void delete(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<String, JobCriteria> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.DELETE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void resume(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<String, JobCriteria> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.RESUME, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, J extends BaseJob> void suspend(JobCriteria criteria, Class<J> jobClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<String, JobCriteria> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, jobClass, Operations.SUSPEND, user);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DATA extends ModelType, J extends BaseJob> void send(ChannelMessage<ID, USER, DATA> message, Class<J> jobClass, String operation, Optional<USER> user) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String channel = createChannel(jobClass, operation);
        asyncClient.send(message, channel, user);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>> ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> createChannelMessage(CriteriaMessage<String, JobCriteria> data, Map<String, Serializable> metadata, String callback) {
        ChannelMessage<ID, USER, CriteriaMessage<String, JobCriteria>> channelMessage = new ChannelMessage<>();
        channelMessage.setMetadata(metadata);
        channelMessage.setData(data);
        channelMessage.setCallback(callback);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>> ChannelMessage<ID, USER, DtoMessage<String, JobDto>> createChannelMessage(DtoMessage<String, JobDto> data, Map<String, Serializable> metadata, String callback) {
        ChannelMessage<ID, USER, DtoMessage<String, JobDto>> channelMessage = new ChannelMessage<>();
        channelMessage.setMetadata(metadata);
        channelMessage.setData(data);
        channelMessage.setCallback(callback);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>> CriteriaMessage<ID, C> createData(C criteria) {
        CriteriaMessage<ID, C> message = new CriteriaMessage<>();
        message.setCriteria(criteria);
        return message;
    }

    public PageableMessage<String, JobCriteria> createData(JobCriteria criteria, Pageable pageable) {
        PageableMessage<String, JobCriteria> message = new PageableMessage<>();
        message.setCriteria(criteria);
        message.setPageable(pageable);
        return message;
    }


    public DtoMessage<String, JobDto> createMessage(JobDto dto) {
        DtoMessage<String, JobDto> message = new DtoMessage<>();
        message.setDto(dto);
        return message;
    }

}
