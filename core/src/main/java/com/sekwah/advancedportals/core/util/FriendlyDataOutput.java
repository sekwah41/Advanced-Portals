package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.serializeddata.BlockLocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;
import java.nio.charset.StandardCharsets;

/**
 * Meant to be similar to FriendlyByteBuf if you have used that before.
 */
public class FriendlyDataOutput {
    private final ByteBuf dataOutput;

    public FriendlyDataOutput() {
        dataOutput = Unpooled.buffer();
    }

    public void writeBoolean(boolean value) {
        dataOutput.writeBoolean(value);
    }

    public void writeByte(byte value) {
        dataOutput.writeByte(value);
    }

    public void writeShort(short value) {
        dataOutput.writeShort(value);
    }

    public void writeInt(int value) {
        dataOutput.writeInt(value);
    }

    public void writeLong(long value) {
        dataOutput.writeLong(value);
    }

    public void writeFloat(float value) {
        dataOutput.writeFloat(value);
    }

    public void writeDouble(double value) {
        dataOutput.writeDouble(value);
    }

    public void writeUtf(String text) {
        this.writeUtf(text, 32767);
    }

    public void writeUtf(String text, int maxLength) {
        if (text.length() > maxLength) {
            throw new EncoderException("String too big (was " + text.length()
                                       + " characters, max " + maxLength + ")");
        } else {
            byte[] abyte = text.getBytes(StandardCharsets.UTF_8);
            int i = getMaxEncodedUtfLength(maxLength);
            if (abyte.length > i) {
                throw new EncoderException("String too big (was " + abyte.length
                                           + " bytes encoded, max " + i + ")");
            } else {
                this.writeVarInt(abyte.length);
                this.writeBytes(abyte);
            }
        }
    }

    public void writeVarInt(int p_130131_) {
        while ((p_130131_ & -128) != 0) {
            this.writeByte(p_130131_ & 127 | 128);
            p_130131_ >>>= 7;
        }

        this.writeByte(p_130131_);
    }

    public void writeBytes(byte[] p_130493_) {
        this.dataOutput.writeBytes(p_130493_);
    }

    public void writeByte(int p_130470_) {
        this.dataOutput.writeByte(p_130470_);
    }

    private static int getMaxEncodedUtfLength(int p_236871_) {
        return p_236871_ * 3;
    }

    public byte[] toByteArray() {
        if (this.dataOutput.readableBytes() <= 0) {
            return new byte[0];
        }

        byte[] bytes = new byte[this.dataOutput.readableBytes()];
        this.dataOutput.readBytes(bytes);
        return bytes;
    }

    // Only for block serialising and such
    private static final int PACKED_X_LENGTH =
        26; // 1 + Mth.log2(Mth.smallestEncompassingPowerOfTwo(30000000)) (im
            // not gonna add all the mojang math stuff to calculate this.
    private static final int PACKED_Z_LENGTH = PACKED_X_LENGTH;
    public static final int PACKED_Y_LENGTH =
        64 - PACKED_X_LENGTH - PACKED_Z_LENGTH;
    private static final long PACKED_X_MASK = (1L << PACKED_X_LENGTH) - 1L;
    private static final long PACKED_Y_MASK = (1L << PACKED_Y_LENGTH) - 1L;
    private static final long PACKED_Z_MASK = (1L << PACKED_Z_LENGTH) - 1L;
    private static final int Z_OFFSET = PACKED_Y_LENGTH;
    private static final int X_OFFSET = PACKED_Y_LENGTH + PACKED_Z_LENGTH;

    public void writeBlock(BlockLocation blockLoc) {
        dataOutput.writeLong(this.blockAsLong(blockLoc));
    }

    private long blockAsLong(BlockLocation blockLoc) {
        long i = 0L;
        i |= ((long) blockLoc.getPosX() & PACKED_X_MASK) << X_OFFSET;
        i |= ((long) blockLoc.getPosY() & PACKED_Y_MASK) << 0;
        return i | ((long) blockLoc.getPosZ() & PACKED_Z_MASK) << Z_OFFSET;
    }
}
