package net.f531.intio.test;

import net.f531.intio.LEB128;
import net.f531.intio.coder.Coder;
import net.f531.intio.coder.Decoder;
import net.f531.intio.coder.Encoder;
import net.f531.intio.stream.IntSink;
import net.f531.intio.stream.IntSource;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class LEB128Test {

    @Test
    public void testFullEncodingUnsigned() {
        BigInteger i = BigInteger.valueOf(410_634_168_746L);
        testFullEncoding(i, LEB128.UNSIGNED);
    }

    @Test
    public void testFullEncodingSignedPositive() {
        BigInteger i = BigInteger.valueOf(410_634_168_746L);
        testFullEncoding(i, LEB128.SIGNED);
    }

    @Test
    public void testFullEncodingSignedNegative() {
        BigInteger i = BigInteger.valueOf(-410_634_168_746L);
        testFullEncoding(i, LEB128.SIGNED);
    }

    @Test
    public void testEncodingUnsigned() {
        BigInteger i     = BigInteger.valueOf(624485);
        byte[]     bytes = encode(i, LEB128.UNSIGNED);
        Assert.assertEquals(3, bytes.length);
        Assert.assertEquals(0xE5, bytes[0] & 0xFF);
        Assert.assertEquals(0x8E, bytes[1] & 0xFF);
        Assert.assertEquals(0x26, bytes[2] & 0xFF);
    }

    @Test
    public void testEncodingSigned() {
        BigInteger i     = BigInteger.valueOf(-123456);
        byte[]     bytes = encode(i, LEB128.SIGNED);
        Assert.assertEquals(3, bytes.length);
        Assert.assertEquals(0xC0, bytes[0] & 0xFF);
        Assert.assertEquals(0xBB, bytes[1] & 0xFF);
        Assert.assertEquals(0x78, bytes[2] & 0xFF);
    }

    @Test
    public void testDecodingSigned() {
        BigInteger i = BigInteger.valueOf(-123456);
        byte[]     b = {(byte) 0xc0, (byte) 0xBB, (byte) 0x78};
        BigInteger j = decode(b, LEB128.SIGNED);
        Assert.assertEquals(i, j);
    }

    @Test
    public void testDecodingUnsigned() {
        BigInteger i = BigInteger.valueOf(624485);
        byte[]     b = {(byte) 0xE5, (byte) 0x8E, (byte) 0x26};
        BigInteger j = decode(b, LEB128.UNSIGNED);
        Assert.assertEquals(i, j);
    }

    private static <T> void testFullEncoding(T value, Coder<T> coder) {
        Assert.assertEquals(value, decode(encode(value, coder), coder));
    }

    private static <T> byte[] encode(T value, Encoder<T> encoder) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        IntConsumer           sink   = IntSink.from(stream);
        encoder.encode(sink, value);
        return stream.toByteArray();
    }

    private static <T> T decode(byte[] bytes, Decoder<T> decoder) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        IntSupplier          source = IntSource.from(stream);
        return decoder.decode(source);
    }

}
