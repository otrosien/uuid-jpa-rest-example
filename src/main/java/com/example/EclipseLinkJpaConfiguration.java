package com.example;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.google.common.collect.ImmutableMap;

@Configuration
@EnableJpaRepositories
@EnableJpaAuditing
@EntityScan(basePackageClasses = {MouseApplication.class, Jsr310JpaConverters.class})
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {

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
        final ImmutableMap<String, Object> immutableMap = ImmutableMap.<String, Object>builder() //
                .put(PersistenceUnitProperties.TABLE_CREATION_SUFFIX, ";")
                .put(PersistenceUnitProperties.SESSION_CUSTOMIZER, com.example.UUIDSequence.class.getName())
                .build();
        return newHashMap(immutableMap);
    }

}
