package net.f531.intio.internal;

import net.f531.intio.internal.function.ThrowingIntConsumer;
import net.f531.intio.internal.function.ThrowingIntSupplier;
import net.f531.intio.internal.function.ThrowingRunnable;

import java.util.Objects;

public class ClosureHandler {

    private final ThrowingRunnable onClose;
    private       boolean          isOpen;

    public ClosureHandler(ThrowingRunnable onClose) {
        this.onClose = Objects.requireNonNull(onClose);
        this.isOpen  = true;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void close() {
        if (this.isOpen()) {
            this.isOpen = false;
            try {
                this.onClose.run();
            }
            catch (Throwable exception) {
                Utils.raise(exception);
            }
        }
    }

    public int invoke(ThrowingIntSupplier supplier) {
        try {
            if (!this.isOpen()) {
                throw new RuntimeException("closed");
            }
            return supplier.get();
        }
        catch (Throwable exception) {
            this.close();
            Utils.raise(exception);
            return -1;
        }
    }

    public void invoke(ThrowingRunnable runnable) {
        this.invoke(() -> {
            runnable.run();
            return -1;
        });
    }

    public void invoke(ThrowingIntConsumer consumer, int value) {
        this.invoke(() -> {
            consumer.accept(value);
        });
    }

}
