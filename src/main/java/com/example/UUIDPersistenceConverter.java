package com.example;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class UUIDPersistenceConverter implements AttributeConverter<UUID, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return ToBytesTransformer.INSTANCE.transform(uuid);
    }

    @Override
    public UUID convertToEntityAttribute(byte[] binaryData) {
        if(binaryData == null) {
            return null;
        }
        return ToBytesTransformer.INSTANCE.parse(binaryData);
    }

    static class ToBytesTransformer {
        public static final ToBytesTransformer INSTANCE = new ToBytesTransformer();

        byte[] transform(UUID uuid) {
            byte[] bytes = new byte[16];
            System.arraycopy( fromLong( uuid.getMostSignificantBits() ), 0, bytes, 0, 8 );
            System.arraycopy( fromLong( uuid.getLeastSignificantBits() ), 0, bytes, 8, 8 );
            return bytes;
        }

        private static byte[] fromLong(long longValue) {
            byte[] bytes = new byte[8];
            bytes[0] = (byte) ( longValue >> 56 );
            bytes[1] = (byte) ( ( longValue << 8 ) >> 56 );
            bytes[2] = (byte) ( ( longValue << 16 ) >> 56 );
            bytes[3] = (byte) ( ( longValue << 24 ) >> 56 );
            bytes[4] = (byte) ( ( longValue << 32 ) >> 56 );
            bytes[5] = (byte) ( ( longValue << 40 ) >> 56 );
            bytes[6] = (byte) ( ( longValue << 48 ) >> 56 );
            bytes[7] = (byte) ( ( longValue << 56 ) >> 56 );
            return bytes;
        }

        UUID parse(Object value) {
            byte[] msb = new byte[8];
            byte[] lsb = new byte[8];
            System.arraycopy( value, 0, msb, 0, 8 );
            System.arraycopy( value, 8, lsb, 0, 8 );
            return new UUID( asLong( msb ), asLong( lsb ) );
        }

        private static long asLong(byte[] bytes) {
            if ( bytes == null ) {
                return 0;
            }
            if ( bytes.length != 8 ) {
                throw new IllegalArgumentException( "Expecting 8 byte values to construct a long" );
            }
            long value = 0;
            for (int i=0; i<8; i++) {
                value = (value << 8) | (bytes[i] & 0xff);
            }
            return value;
        }

    }
}
