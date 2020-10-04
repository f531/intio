package net.f531.intio.internal.impl;

import net.f531.intio.internal.ClosureHandler;
import net.f531.intio.internal.function.ThrowingIntConsumer;
import net.f531.intio.internal.function.ThrowingRunnable;
import net.f531.intio.stream.IntClosable;

import java.io.OutputStream;
import java.util.Objects;

public class OutputStreamImpl extends OutputStream implements IntClosable {

    private final ThrowingIntConsumer consumer;
    private final ClosureHandler      closure;

    public OutputStreamImpl(ThrowingIntConsumer consumer, ThrowingRunnable closure) {
        this.consumer = Objects.requireNonNull(consumer);
        this.closure  = new ClosureHandler(closure);
    }

    @Override
    public void write(int value) {
        this.closure.invoke(this.consumer, value);
    }

    @Override
    public void close() {
        this.closure.close();
    }

    @Override
    public boolean isOpen() {
        return this.closure.isOpen();
    }

}
