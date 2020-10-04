package net.f531.intio.internal.impl;

import net.f531.intio.internal.ClosureHandler;
import net.f531.intio.internal.function.ThrowingIntSupplier;
import net.f531.intio.internal.function.ThrowingRunnable;
import net.f531.intio.stream.IntSource;

import java.util.Objects;

public class IntSourceImpl implements IntSource {

    private final ThrowingIntSupplier supplier;
    private final ClosureHandler      closure;

    public IntSourceImpl(ThrowingIntSupplier supplier, ThrowingRunnable closure) {
        this.supplier = Objects.requireNonNull(supplier);
        this.closure  = new ClosureHandler(closure);
    }

    @Override
    public int getAsInt() {
        return this.closure.invoke(this.supplier);
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
