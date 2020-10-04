package net.f531.intio.coder;

import net.f531.intio.internal.impl.CoderImpl;

import java.util.Objects;

public interface Coder<T> extends Encoder<T>, Decoder<T> {

    default Encoder<? super T> encoder() {
        return this;
    }

    default Decoder<? extends T> decoder() {
        return this;
    }

    static <T> Coder<T> from(Encoder<? super T> encoder, Decoder<? extends T> decoder) {
        Objects.requireNonNull(encoder);
        Objects.requireNonNull(decoder);
        if (encoder == decoder && encoder instanceof Coder<?>) {
            @SuppressWarnings("unchecked")
            Coder<T> coder = (Coder<T>) encoder;
            return coder;
        }
        return new CoderImpl<>(encoder, decoder);
    }

}
