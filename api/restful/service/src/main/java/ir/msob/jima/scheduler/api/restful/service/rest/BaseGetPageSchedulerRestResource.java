package ir.msob.jima.scheduler.api.restful.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.scheduler.commons.BaseSchedulerRepository;
import ir.msob.jima.scheduler.commons.JobCriteria;
import ir.msob.jima.scheduler.commons.JobDto;
import ir.msob.jima.scheduler.service.BaseSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

public interface BaseGetPageSchedulerRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        R extends BaseSchedulerRepository,
        S extends BaseSchedulerService<ID, USER, R>>
        extends ParentSchedulerRestResource<ID, USER, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetPageSchedulerRestResource.class);

    @GetMapping(Operations.GET_PAGE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @MethodStats
    default ResponseEntity<Mono<Page<JobDto>>> getPage(JobCriteria criteria, Pageable pageable, ServerWebExchange serverWebExchange, Principal principal) throws JsonProcessingException {
        log.debug("REST request to getPage job, criteria {}", criteria);
        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.getPageResponse(this.getService().getPage(criteria, pageable, user), user);

    }

    default ResponseEntity<Mono<Page<JobDto>>> getPageResponse(Mono<Page<JobDto>> result, Optional<USER> user) {
        return ResponseEntity.ok(result);
    }
}
