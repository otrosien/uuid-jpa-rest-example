package com.example;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import com.google.common.collect.ImmutableMap;

@Configuration
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final ImmutableMap<String, Object> immutableMap = ImmutableMap.<String, Object>builder() //
                .put(PersistenceUnitProperties.TABLE_CREATION_SUFFIX, ";")
                .build();
        return newHashMap(immutableMap);
    }
}
