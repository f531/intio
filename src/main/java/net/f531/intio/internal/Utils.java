package net.f531.intio.internal;

import net.f531.intio.internal.function.ThrowingRunnable;

public final class Utils {

    private Utils() {}

    public static final ThrowingRunnable NO_OPERATION = () -> {};

    public static ThrowingRunnable closing(Object any) {
        return any instanceof AutoCloseable
                ? ((AutoCloseable) any)::close
                : NO_OPERATION;
    }

    public static <T, X extends Throwable> T raise(Throwable exception) throws X {
        throw Utils.<X>cast(exception);
    }

    public static <T> T cast(Object value) {
        @SuppressWarnings("unchecked")
        T t = (T) value;
        return t;
    }

}
