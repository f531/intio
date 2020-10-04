package net.f531.intio.stream;

public interface IntClosable extends AutoCloseable {

    @Override
    void close();

    boolean isOpen();

}
