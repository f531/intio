package net.f531.intio.stream;

import net.f531.intio.internal.Utils;
import net.f531.intio.internal.impl.IntSinkImpl;

import java.util.Objects;
import java.util.function.IntConsumer;

public interface IntSink extends IntClosable, IntConsumer {

    static IntSink from(IntConsumer consumer) {
        Objects.requireNonNull(consumer);
        return consumer instanceof IntSink
                ? (IntSink) consumer
                : new IntSinkImpl(consumer::accept, Utils.closing(consumer));
    }

    static IntSink from(java.nio.ByteBuffer buffer) {
        Objects.requireNonNull(buffer);
        return new IntSinkImpl(it -> buffer.put((byte) it), Utils.NO_OPERATION);
    }

    static IntSink from(java.io.OutputStream stream) {
        Objects.requireNonNull(stream);
        return new IntSinkImpl(stream::write, stream::close);
    }

    static IntSink from(java.io.Writer stream) {
        Objects.requireNonNull(stream);
        return new IntSinkImpl(stream::write, stream::close);
    }

}
