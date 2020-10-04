package net.f531.intio;

import net.f531.intio.coder.Coder;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public final class LEB128 {

    private LEB128() {}

    public static final Coder<BigInteger> UNSIGNED = Coder.from(LEB128::encodeUnsigned, LEB128::decodeUnsigned);
    public static final Coder<BigInteger> SIGNED   = Coder.from(LEB128::encodeSigned, LEB128::decodeSigned);

    private static final BigInteger ZERO  = BigInteger.ZERO;
    private static final BigInteger M_ONE = BigInteger.ONE.negate();
    private static final int        _0    = 0;
    private static final int        _7    = 7;
    private static final int        _0x7F = 0x7F;
    private static final int        _0x40 = 0x40;
    private static final int        _0x80 = 0x80;

    public static BigInteger decodeUnsigned(IntSupplier source) {
        Objects.requireNonNull(source);
        BigInteger r = ZERO;
        int        s = _0, c;
        do {
            c = source.getAsInt();
            r = r.or(BigInteger.valueOf(c & _0x7F).shiftLeft(s));
            s += _7;
        }
        while ((c & _0x80) != _0);
        return r;
    }

    public static BigInteger decodeSigned(IntSupplier source) {
        Objects.requireNonNull(source);
        BigInteger r = ZERO;
        int        s = _0, c;
        do {
            c = source.getAsInt();
            r = r.or(BigInteger.valueOf(c & _0x7F).shiftLeft(s));
            s += _7;
        }
        while ((c & _0x80) != _0);
        if ((c & _0x40) != _0) {
            r = r.or(ZERO.not().shiftLeft(s));
        }
        return r;
    }

    public static void encodeUnsigned(IntConsumer sink, BigInteger value) {
        Objects.requireNonNull(sink);
        Objects.requireNonNull(value);
        if (value.signum() < 0) {
            throw new IllegalArgumentException();
        }
        int c;
        do {
            c     = value.intValue() & _0x7F;
            value = value.shiftRight(_7);
            if (!ZERO.equals(value)) {
                c |= _0x80;
            }
            sink.accept(c);
        }
        while (!ZERO.equals(value));
    }

    public static void encodeSigned(IntConsumer sink, BigInteger value) {
        Objects.requireNonNull(sink);
        Objects.requireNonNull(value);
        boolean m = true;
        int     c;
        while (m) {
            c     = value.intValue() & _0x7F;
            value = value.shiftRight(_7);
            if ((ZERO.equals(value) && (c & _0x40) == _0) || (M_ONE.equals(value) && (c & _0x40) != _0)) {
                m = false;
            }
            else {
                c |= _0x80;
            }
            sink.accept(c);
        }
    }

}
