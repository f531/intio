package net.f531.intio.coder;

import java.util.function.IntSupplier;

@FunctionalInterface
public interface Decoder<T> {

    T decode(IntSupplier source);

}
