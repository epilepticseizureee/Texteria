package touchable.texteria.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class TexteriaUtil {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull String asHex(byte[] data) {
        return asHex(data, 0, data.length);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull String asHex(byte[] data, int offset, int length) {
        char[] buf = new char[length * 2];
        int x = 0;
        for (int i = offset; i < offset + length; ++i) {
            buf[x++] = HEX_CHARS[data[i] >>> 4 & 0xF];
            buf[x++] = HEX_CHARS[data[i] & 0xF];
        }
        return new String(buf);
    }
}
