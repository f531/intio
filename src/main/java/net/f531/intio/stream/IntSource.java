package net.f531.intio.stream;

import net.f531.intio.internal.Utils;
import net.f531.intio.internal.impl.IntSourceImpl;

import java.util.Objects;
import java.util.function.IntSupplier;

public interface IntSource extends IntClosable, IntSupplier {

    static IntSource from(IntSupplier supplier) {
        Objects.requireNonNull(supplier);
        return supplier instanceof IntSource
                ? (IntSource) supplier
                : new IntSourceImpl(supplier::getAsInt, Utils.closing(supplier));
    }

    static IntSource from(java.nio.ByteBuffer buffer) {
        Objects.requireNonNull(buffer);
        return new IntSourceImpl(buffer::get, Utils.NO_OPERATION);
    }

    static IntSource from(java.io.InputStream stream) {
        Objects.requireNonNull(stream);
        return new IntSourceImpl(() -> {
            int read = stream.read();
            if ((read & 0xFF) != read) {
                throw new java.io.EOFException();
            }
            return read;
        }, stream::close);
    }

    static IntSource from(java.io.Reader stream) {
        Objects.requireNonNull(stream);
        return new IntSourceImpl(() -> {
            int read = stream.read();
            if ((read & 0xFFFF) != read) {
                throw new java.io.EOFException();
            }
            return read;
        }, stream::close);
    }


}
