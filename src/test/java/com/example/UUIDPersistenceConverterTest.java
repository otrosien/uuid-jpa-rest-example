package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.Test;

public class UUIDPersistenceConverterTest {

    @Test
    public void testRoundtrip() {
        UUIDPersistenceConverter converter = new UUIDPersistenceConverter();
        UUID uuid = UUID.fromString("e09a352e-e6c6-11e5-924a-1bd8875d2c25");
        assertEquals(uuid, converter.convertToEntityAttribute(converter.convertToDatabaseColumn(uuid)));
    }

    @Test
    public void testNull() {
        UUIDPersistenceConverter converter = new UUIDPersistenceConverter();
        assertNull(converter.convertToEntityAttribute(null));
        assertNull(converter.convertToDatabaseColumn(null));
    }

}
