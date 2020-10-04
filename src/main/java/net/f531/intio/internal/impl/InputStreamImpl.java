package net.f531.intio.internal.impl;

import net.f531.intio.internal.ClosureHandler;
import net.f531.intio.internal.function.ThrowingIntSupplier;
import net.f531.intio.internal.function.ThrowingRunnable;
import net.f531.intio.stream.IntClosable;

import java.io.InputStream;
import java.util.Objects;

public class InputStreamImpl extends InputStream implements IntClosable {

    private final ThrowingIntSupplier supplier;
    private final ClosureHandler      closure;

    public InputStreamImpl(ThrowingIntSupplier supplier, ThrowingRunnable closure) {
        this.supplier = Objects.requireNonNull(supplier);
        this.closure  = new ClosureHandler(closure);
    }

    @Override
    public int read() {
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
