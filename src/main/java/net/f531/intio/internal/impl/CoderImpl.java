package net.f531.intio.internal.impl;

import net.f531.intio.coder.Coder;
import net.f531.intio.coder.Decoder;
import net.f531.intio.coder.Encoder;

import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class CoderImpl<T> implements Coder<T> {

    private final Encoder<? super T> encoder;
    private final Decoder<? extends T> decoder;

    public CoderImpl(Encoder<? super T> encoder, Decoder<? extends T> decoder) {
        this.encoder = Objects.requireNonNull(encoder);
        this.decoder = Objects.requireNonNull(decoder);
    }

    @Override
    public T decode(IntSupplier source) {
        Objects.requireNonNull(source);
        return this.decoder().decode(source);
    }

    @Override
    public void encode(IntConsumer sink, T value) {
        Objects.requireNonNull(sink);
        this.encoder().encode(sink, value);
    }

    @Override
    public Encoder<? super T> encoder() {
        return this.encoder;
    }

    @Override
    public Decoder<? extends T> decoder() {
        return this.decoder;
    }

}
