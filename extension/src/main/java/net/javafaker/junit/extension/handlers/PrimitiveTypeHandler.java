package net.javafaker.junit.extension.handlers;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;

class PrimitiveTypeHandler implements FakeDataHandler {

    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return Number.class.isAssignableFrom(type)
                || type.isPrimitive()
                || Boolean.class.equals(type);
    }

    @Override
    public Object handle(Class<?> type, Annotation annotation) {
        var random = getFaker().random();
        if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return random.nextBoolean();
        } else if (type.equals(byte.class)
                || type.equals(Byte.class)) {
            return ByteBuffer.wrap(random.nextRandomBytes(1)).get();
        } else if (type.equals(char.class)) {
            return ByteBuffer.wrap(random.nextRandomBytes(2)).getChar();
        } else if (type.equals(short.class) || type.equals(Short.class)) {
            return ByteBuffer.wrap(random.nextRandomBytes(2)).getShort();
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return random.nextLong();
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return random.nextDouble();
        } else {
            return null;
        }
    }
}
