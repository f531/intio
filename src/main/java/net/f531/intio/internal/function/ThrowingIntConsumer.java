package net.f531.intio.internal.function;

@FunctionalInterface
public interface ThrowingIntConsumer {

    void accept(int value) throws Throwable;

}
