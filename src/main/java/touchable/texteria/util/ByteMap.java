package touchable.texteria.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ByteMap extends HashMap<String, Object> {
    private static final byte TYPE_INT = 1;
    private static final byte TYPE_BYTE = 2;
    private static final byte TYPE_LONG = 3;
    private static final byte TYPE_STRING = 4;
    private static final byte TYPE_SHORT = 5;
    private static final byte TYPE_FLOAT = 6;
    private static final byte TYPE_DOUBLE = 7;
    private static final byte TYPE_BOOLEAN = 8;
    private static final byte TYPE_MAP = 9;
    private static final byte TYPE_BYTE_ARRAY = 10;
    private static final byte TYPE_STRING_ARRAY = 11;
    private static final byte TYPE_MAP_ARRAY = 12;
    private static final byte TYPE_VARINT = 13;
    private static final byte TYPE_LONG_VARINT = 14;
    private static final byte TYPE_UUID = 15;
    private static final byte TYPE_VARINT_ARRAY = 16;
    private static final byte TYPE_SIGNED_VARINT = 17;
    private static final byte TYPE_SIGNED_VARINT_ARRAY = 18;
    private static final byte TYPE_INT_ARRAY = 19;
    private static final byte TYPE_STRING_ARRAY_2D = 20;

    public ByteMap() {
    }

    public ByteMap(HashMap<String, Object> map) {
        super(map);
    }

    public ByteMap(byte[] bytes) {
        this(new PacketDataSerializer(Unpooled.wrappedBuffer(bytes)));
    }

    public ByteMap(PacketDataSerializer in) {
        try {
            while (in.readerIndex() < in.capacity()) {
                String key = in.c(256);
                switch (in.readByte()) {
                    case 1: {
                        this.put(key, in.readInt());
                        break;
                    }
                    case 13: {
                        this.put(key, in.e());
                        break;
                    }
                    case 17: {
                        this.put(key, ByteMap.readSignedVarInt(in));
                        break;
                    }
                    case 2: {
                        this.put(key, in.readByte());
                        break;
                    }
                    case 3: {
                        this.put(key, in.readLong());
                        break;
                    }
                    case 14: {
                        this.put(key, (long) in.e());
                        break;
                    }
                    case 4: {
                        this.put(key, in.c(32767));
                        break;
                    }
                    case 5: {
                        this.put(key, in.readShort());
                        break;
                    }
                    case 6: {
                        this.put(key, in.readFloat());
                        break;
                    }
                    case 7: {
                        this.put(key, in.readDouble());
                        break;
                    }
                    case 8: {
                        this.put(key, in.readBoolean());
                        break;
                    }
                    case 15: {
                        this.put(key, new UUID(in.readLong(), in.readLong()));
                        break;
                    }
                    case 9: {
                        byte[] mapBytes = new byte[in.e()];
                        in.readBytes(mapBytes, 0, mapBytes.length);
                        this.put(key, new ByteMap(mapBytes));
                        break;
                    }
                    case 10: {
                        byte[] arr = new byte[in.e()];
                        in.readBytes(arr, 0, arr.length);
                        this.put(key, arr);
                        break;
                    }
                    case 19: {
                        int i;
                        int[] arr = new int[in.e()];
                        for (i = 0; i < arr.length; ++i)
                            arr[i] = in.readInt();
                        this.put(key, arr);
                        break;
                    }
                    case 16: {
                        int i;
                        int[] arr = new int[in.e()];
                        for (i = 0; i < arr.length; ++i)
                            arr[i] = in.e();
                        this.put(key, arr);
                        break;
                    }
                    case 18: {
                        int i;
                        int[] arr = new int[in.e()];
                        for (i = 0; i < arr.length; ++i)
                            arr[i] = ByteMap.readSignedVarInt(in);
                        this.put(key, arr);
                        break;
                    }
                    case 11: {
                        int i;
                        String[] arr = new String[in.e()];
                        for (i = 0; i < arr.length; ++i)
                            arr[i] = in.c(32767);
                        this.put(key, arr);
                        break;
                    }
                    case 12: {
                        int i;
                        ByteMap[] arr = new ByteMap[in.e()];
                        for (i = 0; i < arr.length; ++i) {
                            byte[] mapBytes = new byte[in.e()];
                            in.readBytes(mapBytes, 0, mapBytes.length);
                            arr[i] = new ByteMap(mapBytes);
                        }
                        this.put(key, arr);
                        break;
                    }
                    case 20: {
                        int i;
                        String[][] arr = new String[in.e()][];
                        for (i = 0; i < arr.length; ++i) {
                            arr[i] = new String[in.e()];
                            for (int k = 0; k < arr.length; ++k)
                                arr[i][k] = in.c(32767);
                        }
                        this.put(key, arr);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readSignedVarInt(PacketDataSerializer buf) {
        int raw = buf.e();
        int temp = (raw << 31 >> 31 ^ raw) >> 1;
        return temp ^ raw & Integer.MIN_VALUE;
    }

    private static void writeSignedVarInt(PacketDataSerializer buf, int val) {
        buf.b(val << 1 ^ val >> 31);
    }

    public byte[] toByteArray() {
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
        try {
            PacketDataSerializer out = new PacketDataSerializer(buffer);
            for (Map.Entry<String, Object> entry : this.entrySet()) {
                out.a(entry.getKey());
                Object val = entry.getValue();
                Class<?> clazz = val.getClass();
                if (clazz == Integer.class) {
                    int num = (Integer) val;
                    if (num >= 0 && num < 0x200000) {
                        out.writeByte(13);
                        out.b(num);
                        continue;
                    }
                    if (num < 0 && num > -1048576) {
                        out.writeByte(17);
                        ByteMap.writeSignedVarInt(out, num);
                        continue;
                    }
                    out.writeByte(1);
                    out.writeInt(num);
                    continue;
                }
                if (clazz == Float.class) {
                    out.writeByte(6);
                    out.writeFloat((Float) val);
                    continue;
                }
                if (clazz == Byte.class) {
                    out.writeByte(2);
                    out.writeByte((Byte) val);
                    continue;
                }
                if (clazz == Short.class) {
                    out.writeByte(5);
                    out.writeShort((Short) val);
                    continue;
                }
                if (clazz == Long.class) {
                    long num = (Long) val;
                    if (num >= 0L && num < 0x200000L) {
                        out.writeByte(14);
                        out.b((int) num);
                        continue;
                    }
                    out.writeByte(3);
                    out.writeLong(num);
                    continue;
                }
                if (clazz == String.class) {
                    out.writeByte(4);
                    out.a((String) val);
                    continue;
                }
                if (clazz == Double.class) {
                    out.writeByte(7);
                    out.writeDouble((Double) val);
                    continue;
                }
                if (clazz == Boolean.class) {
                    out.writeByte(8);
                    out.writeBoolean((Boolean) val);
                    continue;
                }
                if (clazz == UUID.class) {
                    out.writeByte(15);
                    UUID uuid = (UUID) val;
                    out.writeLong(uuid.getMostSignificantBits());
                    out.writeLong(uuid.getLeastSignificantBits());
                    continue;
                }
                if (clazz == ByteMap.class) {
                    out.writeByte(9);
                    byte[] bytes = ((ByteMap) val).toByteArray();
                    out.b(bytes.length);
                    out.writeBytes(bytes);
                    continue;
                }
                if (clazz == byte[].class) {
                    out.writeByte(10);
                    byte[] bytes = (byte[]) val;
                    out.b(bytes.length);
                    out.writeBytes(bytes);
                    continue;
                }
                if (clazz == int[].class) {
                    int[] arr = (int[]) val;
                    if (arr.length == 0) {
                        out.writeByte(16);
                        out.writeByte(0);
                        continue;
                    }
                    boolean varInt = true;
                    boolean signedVarInt = false;
                    for (int i = 0; i < 4 && i < arr.length; ++i) {
                        if (arr[i] < 0 || arr[i] > 0x200000) {
                            varInt = false;
                        }
                        if (arr[i] >= 0 || arr[i] <= -1048576) continue;
                        signedVarInt = true;
                    }
                    if (varInt) {
                        out.writeByte(16);
                        out.b(arr.length);
                        for (int v : arr) {
                            out.b(v);
                        }
                        continue;
                    }
                    if (signedVarInt) {
                        out.writeByte(18);
                        out.b(arr.length);
                        for (int v : arr) {
                            ByteMap.writeSignedVarInt(out, v);
                        }
                        continue;
                    }
                    out.writeByte(19);
                    out.b(arr.length);
                    for (int v : arr) {
                        out.writeInt(v);
                    }
                    continue;
                }
                if (clazz == String[].class) {
                    out.writeByte(11);
                    String[] arr = (String[]) val;
                    out.b(arr.length);
                    for (String str : arr) {
                        out.a(str);
                    }
                    continue;
                }
                if (clazz == String[][].class) {
                    out.writeByte(20);
                    String[][] arr = (String[][]) val;
                    out.b(arr.length);
                    for (String[] arr0 : arr) {
                        out.b(arr0.length);
                        for (String str : arr0) {
                            out.a(str);
                        }
                    }
                    continue;
                }
                if (clazz == ByteMap[].class) {
                    out.writeByte(12);
                    ByteMap[] arr = (ByteMap[]) val;
                    out.b(arr.length);
                    for (ByteMap map : arr) {
                        byte[] serialized = map.toByteArray();
                        out.b(serialized.length);
                        out.writeBytes(serialized);
                    }
                    continue;
                }
                throw new IllegalStateException("Unknown value type " + clazz + " for key '" + entry.getKey() + "'");
            }
            byte[] bytes = new byte[buffer.writerIndex()];
            buffer.readBytes(bytes);
            buffer.release();
            return bytes;
        } catch (Exception e) {
            buffer.release();
            e.printStackTrace();
            return new byte[0];
        }
    }

    public String getString(String key) {
        return (String) this.get(key);
    }

    public byte getByte(String key) {
        return ((Number) this.get(key)).byteValue();
    }

    public short getShort(String key) {
        return ((Number) this.get(key)).shortValue();
    }

    public float getFloat(String key) {
        return ((Number) this.get(key)).floatValue();
    }

    public double getDouble(String key) {
        return ((Number) this.get(key)).doubleValue();
    }

    public int getInt(String key) {
        return ((Number) this.get(key)).intValue();
    }

    public long getLong(String key) {
        return ((Number) this.get(key)).longValue();
    }

    public boolean getBoolean(String key) {
        return (Boolean) this.get(key);
    }

    public UUID getUUID(String key) {
        return (UUID) this.get(key);
    }

    public ByteMap getMap(String key) {
        return (ByteMap) this.get(key);
    }

    public byte[] getByteArray(String key) {
        return (byte[]) this.get(key);
    }

    public int[] getIntArray(String key) {
        return (int[]) this.get(key);
    }

    public String[] getStringArray(String key) {
        return (String[]) this.get(key);
    }

    public ByteMap[] getMapArray(String key) {
        return (ByteMap[]) this.get(key);
    }

    public String getString(String key, String def) {
        Object o = this.get(key);
        return o == null ? def : (String) o;
    }

    public byte getByte(String key, byte def) {
        Object o = this.get(key);
        return o == null ? def : ((Number) this.get(key)).byteValue();
    }

    public short getShort(String key, short def) {
        Object o = this.get(key);
        return o == null ? def : ((Number) this.get(key)).shortValue();
    }

    public float getFloat(String key, float def) {
        Object o = this.get(key);
        return o == null ? def : ((Number) this.get(key)).floatValue();
    }

    public double getDouble(String key, double def) {
        Object o = this.get(key);
        return o == null ? def : ((Number) this.get(key)).doubleValue();
    }

    public int getInt(String key, int def) {
        Object o = this.get(key);
        return o == null ? def : ((Number) this.get(key)).intValue();
    }

    public long getLong(String key, long def) {
        Object o = this.get(key);
        return o == null ? def : ((Number) this.get(key)).longValue();
    }

    public boolean getBoolean(String key, boolean def) {
        Object o = this.get(key);
        return o == null ? def : (Boolean) o;
    }

    public ByteMap getMap(String key, ByteMap def) {
        Object o = this.get(key);
        return o == null ? def : (ByteMap) o;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        Iterator<String> it = this.keySet().stream().sorted().collect(Collectors.toList()).iterator();
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        while (true) {
            String val;
            String key = it.next();
            Object value = this.get(key);
            sb.append(key);
            sb.append('=');
            if (value == this) {
                val = "(this Map)";
            } else if (value instanceof byte[]) {
                byte[] bval = (byte[]) value;
                val = TexteriaUtil.asHex(bval, 0, Math.min(64, bval.length));
                if (bval.length > 64) {
                    val = val + "... (" + bval.length + ")";
                }
            } else {
                val = value instanceof int[] ? Arrays.toString((int[]) value) : (value instanceof Object[] ? Arrays.deepToString((Object[]) value) : String.valueOf(value));
            }
            sb.append(val);
            if (!it.hasNext()) {
                return sb.append('}').toString();
            }
            sb.append(',').append(' ');
        }
    }
}

