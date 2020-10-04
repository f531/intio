package net.f531.intio.coder;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface Encoder<T> {

    void encode(IntConsumer sink, T value);

}
