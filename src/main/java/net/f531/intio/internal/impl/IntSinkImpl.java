package net.f531.intio.internal.impl;

import net.f531.intio.internal.ClosureHandler;
import net.f531.intio.internal.function.ThrowingIntConsumer;
import net.f531.intio.internal.function.ThrowingRunnable;
import net.f531.intio.stream.IntSink;

import java.util.Objects;

public class IntSinkImpl implements IntSink {

    private final ThrowingIntConsumer consumer;
    private final ClosureHandler      closure;

    public IntSinkImpl(ThrowingIntConsumer consumer, ThrowingRunnable closure) {
        this.consumer = Objects.requireNonNull(consumer);
        this.closure  = new ClosureHandler(closure);
    }

    @Override
    public void accept(int value) {
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
