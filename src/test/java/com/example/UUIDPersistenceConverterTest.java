package com.example;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class UUIDPersistenceConverterTest {

    @Test
    public void testNull() {
        UUIDPersistenceConverter converter = new UUIDPersistenceConverter();
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    public void testRoundtrip() {
        UUIDPersistenceConverter converter = new UUIDPersistenceConverter();
        UUID uuid = new UUID(1l, 2l);
        assertEquals(uuid, converter.convertToEntityAttribute(converter.convertToDatabaseColumn(uuid)));
    }

    @Test
    public void testToByteArray() {
        UUIDPersistenceConverter converter = new UUIDPersistenceConverter();
        UUID uuid = new UUID(1l, 2l);
        assertArrayEquals(converter.convertToDatabaseColumn(uuid), new byte[] {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidByteArray() {
        UUIDPersistenceConverter converter = new UUIDPersistenceConverter();
        converter.convertToEntityAttribute(new byte[] {0});
    }
}
