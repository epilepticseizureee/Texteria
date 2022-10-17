package touchable.texteria.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import touchable.texteria.element.Element2D;
import touchable.texteria.misc.Position;

@SuppressWarnings("all")
public class OffsetUtils {
    @Contract("_, _ -> param1")
    public static <T extends Element2D<?>> @NotNull T doubleChest(T elem, int slot) {
        if (slot < 0 || slot > 53) {
            throw new IllegalArgumentException("Slot must be between 0 and 53");
        }
        elem.setPosition(Position.CENTER).setXY(slot % 9 * 18 - 72, slot / 9 * 18 - 85);
        return elem;
    }

    @Contract("_, _ -> param1")
    public static <T extends Element2D<?>> @NotNull T chest(T elem, int slot) {
        if (slot < 0 || slot >= 26) {
            throw new IllegalArgumentException("Slot must be between 0 and 26");
        }
        elem.setPosition(Position.CENTER).setXY(slot % 9 * 18 - 72, slot / 9 * 18 - 58);
        return elem;
    }

    @Contract("_, _ -> param1")
    public static <T extends Element2D<?>> @NotNull T inv9(T elem, int slot) {
        if (slot < 0 || slot > 8) {
            throw new IllegalArgumentException("Slot must be between 0 and 8");
        }
        elem.setPosition(Position.CENTER).setXY(slot * 18 - 72, -40);
        return elem;
    }

    @Contract("_, _ -> param1")
    public static <T extends Element2D<?>> @NotNull T inv45(T elem, int slot) {
        if (slot < 0 || slot > 44) {
            throw new IllegalArgumentException("Slot must be between 0 and 44");
        }
        elem.setPosition(Position.CENTER).setXY(slot % 9 * 18 - 72, slot / 9 * 18 - 76);
        return elem;
    }

    @Contract("_, _ -> param1")
    public static <T extends Element2D<?>> @NotNull T hotbar(T elem, int slot) {
        if (slot < 0 || slot > 8) {
            throw new IllegalArgumentException("Slot must be between 0 and 8");
        }
        elem.setPosition(Position.BOTTOM).setXY(slot * 20 - 80, 3);
        return elem;
    }
}

