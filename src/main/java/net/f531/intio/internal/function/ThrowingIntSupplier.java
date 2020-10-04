package net.f531.intio.internal.function;

@FunctionalInterface
public interface ThrowingIntSupplier {

    int get() throws Throwable;

}
