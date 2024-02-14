package ir.msob.jima.scheduler.ral.quartz.jdbc.nativesupport;

import ir.msob.jima.core.commons.Constants;
import ir.msob.jima.core.commons.util.NativeUtil;
import ir.msob.jima.scheduler.commons.BaseJob;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.simpl.SimpleInstanceIdGenerator;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

public class QuartzRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        NativeUtil.register(hints, SimpleInstanceIdGenerator.class);
        NativeUtil.register(hints, LocalDataSourceJobStore.class);
        NativeUtil.register(hints, PostgreSQLDelegate.class);

        NativeUtil.registerBySupper(hints, Constants.FRAMEWORK_PACKAGE_PREFIX, BaseJob.class);
    }
}
