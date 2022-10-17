package touchable.texteria.util;

import lombok.NonNull;
import org.bukkit.Location;

@SuppressWarnings("unused")
public class PositionFloat {
    private final float x;
    private final float y;
    private final float z;

    public PositionFloat(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PositionFloat(@NonNull Location location) {
        this.x = (float) location.getX();
        this.y = (float) location.getY();
        this.z = (float) location.getZ();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
