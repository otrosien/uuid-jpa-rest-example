package com.example;

import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving.DISABLED;

import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder.EntityManagerFactoryBeanCallback;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.google.common.collect.ImmutableMap;

@Configuration
@ConditionalOnClass(org.eclipse.persistence.jpa.PersistenceProvider.class)
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EclipseLinkJpaConfiguration.class);

    @Autowired(required=false)
    private LoadTimeWeaver loadTimeWeaver;

    protected EclipseLinkJpaConfiguration(DataSource dataSource, JpaProperties properties,
            ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
        super(dataSource, properties, jtaTransactionManagerProvider);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final String weave = loadTimeWeaver == null ? "false":"true";
        final ImmutableMap<String, Object> immutableMap = ImmutableMap.<String, Object>builder() //
                .put(PersistenceUnitProperties.TABLE_CREATION_SUFFIX, ";")
                .put(PersistenceUnitProperties.WEAVING, weave) //
                .build();
        return newHashMap(immutableMap);
    }

    @Configuration
    @ConditionalOnExpression("T(org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver).isInstrumentationAvailable()")
    @EnableLoadTimeWeaving(aspectjWeaving = DISABLED)
    public static class LoadTimeWeavingConfiguration {
    }

    @Configuration
    @ConditionalOnExpression("!T(org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver).isInstrumentationAvailable()")
    public static class NoLoadTimeWeavingConfiguration implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            log.warn("Instrumentation unavailable! No loadtime-weaving is being performed.");
        }
    }

    @Override
    protected EntityManagerFactoryBeanCallback getVendorCallback() {
        // loadTimeWeaver is nullable, but we're only passing it to a simple setter.
        return factory -> factory.setLoadTimeWeaver(loadTimeWeaver);
    }

}
