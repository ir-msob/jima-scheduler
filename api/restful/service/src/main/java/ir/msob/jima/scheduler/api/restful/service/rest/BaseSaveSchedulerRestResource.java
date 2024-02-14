package ir.msob.jima.scheduler.api.restful.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.scheduler.commons.BaseJob;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobDto;
import ir.msob.jima.scheduler.service.BaseSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

public interface BaseSaveSchedulerRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        J extends BaseJob,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends ParentSchedulerRestResource<ID, USER, R, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveSchedulerRestResource.class);

    default Class<J> getJobClass() {
        return (Class<J>) GenericTypeUtil.resolveTypeArguments(getClass(), BaseSaveSchedulerRestResource.class, 2);
    }

    @PostMapping(Operations.SAVE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @MethodStats
    default ResponseEntity<Mono<JobDto>> save(@RequestBody JobDto dto, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to save job, dto {}", dto);
        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.saveResponse(this.getService().save(dto, getJobClass(), user), user);

    }

    default ResponseEntity<Mono<JobDto>> saveResponse(Mono<JobDto> result, Optional<USER> user) {
        return ResponseEntity.ok(result);
    }
}
