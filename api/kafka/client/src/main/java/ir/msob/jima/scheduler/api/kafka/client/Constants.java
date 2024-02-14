package ir.msob.jima.scheduler.api.kafka.client;

import ir.msob.jima.core.commons.annotation.domain.DomainService;

public class Constants {
    private Constants() {
    }

    public static String getChannel(String microservice, String domain, String operation) {
        return String.format("%s_%s_%s",
                        microservice,
                        domain,
                        operation)
                .toLowerCase();
    }

    public static String getChannel(Class<?> clazz, String operation) {
        DomainService domainService = DomainService.info.getAnnotation(clazz);
        return Constants.getChannel(domainService.serviceName(), domainService.domainName(), operation);
    }
}
