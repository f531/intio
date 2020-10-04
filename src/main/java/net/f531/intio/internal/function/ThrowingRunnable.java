package net.f531.intio.internal.function;

@FunctionalInterface
public interface ThrowingRunnable {

    void run() throws Throwable;

}
