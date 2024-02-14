package ir.msob.jima.scheduler.ral.quartz.jdbc.nativesupport;

import ir.msob.jima.core.commons.profile.ProfileConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.Profile;

@Profile(ProfileConstants.NATIVE)
@ImportRuntimeHints(QuartzRuntimeHints.class)
@Configuration
public class QuartzNativeSupport {
}
