package ir.msob.jima.scheduler.ral.quartz.jdbc;

import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import ir.msob.jima.scheduler.commons.schedule.CronSchedule;
import ir.msob.jima.scheduler.ral.quartz.commons.QuartzJob;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.quartz.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class QuartzRepository implements BaseSchedulerRepository {

    private final SchedulerFactoryBean schedulerFactory;

    @SneakyThrows
    @Override
    public <J extends BaseJob> Mono<JobDto> save(JobDto dto, Class<J> jobClass) {
        Class<QuartzJob> quartzJobClass = (Class<QuartzJob>) jobClass;
        schedule(dto, quartzJobClass);
        return Mono.just(dto);
    }

    @SneakyThrows
    @Override
    public Mono<Boolean> delete(JobCriteria criteria) {
        schedulerFactory.getScheduler().deleteJob(prepareJobKey(criteria));
        return Mono.just(true);
    }

    @SneakyThrows
    @Override
    public Mono<Boolean> suspend(JobCriteria criteria) {
        schedulerFactory.getScheduler().pauseJob(prepareJobKey(criteria));
        return Mono.just(true);
    }

    @SneakyThrows
    @Override
    public Mono<Boolean> resume(JobCriteria criteria) {
        schedulerFactory.getScheduler().resumeJob(prepareJobKey(criteria));
        return Mono.just(true);
    }

    @SneakyThrows
    @Override
    public Mono<JobDto> getOne(JobCriteria criteria) {
        JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(prepareJobKey(criteria));
        return Mono.just(prepareJobDto(jobDetail, criteria));
    }

    private JobDto prepareJobDto(JobDetail jobDetail, JobCriteria criteria) {
        JobDto dto = new JobDto();
        dto.setGroup(criteria.getGroup().getEq());
        dto.setIdentity(criteria.getIdentity().getEq());
        dto.setParams(jobDetail.getJobDataMap());
        return dto;
    }

    @Override
    public Mono<Collection<JobDto>> getMany(JobCriteria criteria) {
        return Mono.empty();
    }

    @Override
    public Mono<Page<JobDto>> getPage(JobCriteria criteria, Pageable pageable) {
        return null;
    }

    public void schedule(JobDto dto, Class<QuartzJob> jobClass) throws SchedulerException {
        JobDetail jobDetail = prepareJobDetail(dto, jobClass);
        prepareJobDetailParams(jobDetail, dto);
        Trigger trigger = prepareTrigger(dto);
        Scheduler scheduler = prepareSchedule(jobDetail, trigger);
        scheduler.start();
    }

    private JobDetail prepareJobDetail(JobDto dto, Class<QuartzJob> jobClass) {
        return JobBuilder.newJob(jobClass).withIdentity(dto.getIdentity(), dto.getGroup()).build();
    }

    private void prepareJobDetailParams(JobDetail jobDetail, JobDto dto) {
        for (Map.Entry<String, Object> entry : dto.getParams().entrySet()) {
            jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
        }
    }

    private Scheduler prepareSchedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        return scheduler;
    }

    private Trigger prepareTrigger(JobDto dto) {
        TriggerBuilder<Trigger> builder = TriggerBuilder
                .newTrigger()
                .withIdentity(dto.getIdentity(), dto.getGroup());

        if (dto.getSchedule() instanceof CronSchedule schedule) {
            if (Strings.isNotBlank(schedule.getCron()))
                builder
                        .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCron()));
        }

        if (dto.getSchedule().getStartAt() != null) {
            builder
                    .startAt(Date.from(dto.getSchedule().getStartAt()));
        }

        return builder
                .build();

    }

    private JobKey prepareJobKey(JobCriteria criteria) {
        return new JobKey(criteria.getIdentity().getEq(), criteria.getGroup().getEq());
    }

}
