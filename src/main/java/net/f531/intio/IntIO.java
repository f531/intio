package net.f531.intio;

import net.f531.intio.internal.Utils;
import net.f531.intio.internal.impl.InputStreamImpl;
import net.f531.intio.internal.impl.OutputStreamImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public final class IntIO {

    private IntIO() {}

    public static void transfer(IntSupplier source, IntConsumer sink, long count) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(sink);
        if (count < 0) { throw new IllegalArgumentException(); }
        for (long i = 0; i < count; i++) {
            sink.accept(source.getAsInt());
        }
    }

    public static InputStream toInputStream(IntSupplier source) {
        Objects.requireNonNull(source);
        return new InputStreamImpl(source::getAsInt, Utils.closing(source));
    }

    public static OutputStream toOutputStream(IntConsumer sink) {
        Objects.requireNonNull(sink);
        return new OutputStreamImpl(sink::accept, Utils.closing(sink));
    }

}
