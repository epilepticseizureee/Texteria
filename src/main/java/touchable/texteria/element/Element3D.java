package touchable.texteria.element;

import touchable.texteria.util.ByteMap;
import touchable.texteria.misc.IWritable;

@SuppressWarnings("all")
public abstract class Element3D<E extends Element3D<E>> implements IWritable {
    private final String id;
    private long duration = -1;
    private int startFade = 255;
    private int finishFade = 255;
    private int renderDistanceSquared = 64;
    private boolean hoverable;
    private int hoverRangeSquared = 10;
    private boolean frustum = true;

    public Element3D(String id) {
        this.id = id;
    }

    public E setDuration(long duration) {
        this.duration = duration;
        return (E) this;
    }

    public E setFadeStart(int fade) {
        this.startFade = fade;
        return (E) this;
    }

    public E setFadeFinish(int fade) {
        this.finishFade = fade;
        return (E) this;
    }

    public E setFade(int fade) {
        this.finishFade = this.startFade = fade;
        return (E) this;
    }

    public E setRenderDistance(int renderDistance) {
        this.renderDistanceSquared = renderDistance;
        return (E) this;
    }

    public E setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
        return (E) this;
    }

    public E setHoverRange(int hoverRange) {
        this.hoverRangeSquared = hoverRange;
        return (E) this;
    }

    public E setFrustum(boolean frustum) {
        this.frustum = frustum;
        return (E) this;
    }

    public abstract String getElementType();

    @Override
    public ByteMap write(ByteMap map) {
        map.put("type", getElementType());
        map.put("id", id);

        if (duration != -1) {
            map.put("dur", duration);
        }
        if (startFade != 255 || finishFade != 255) {
            if (startFade == finishFade) {
                map.put("fade", startFade);
            } else {
                if (startFade != 255) {
                    map.put("fade.s", startFade);
                }
                if (finishFade != 255) {
                    map.put("fade.f", finishFade);
                }
            }
        }
        if (renderDistanceSquared != 64) {
            map.put("rndrDist", renderDistanceSquared);
        }
        if (hoverable) {
            map.put("hv", true);
        }
        if (hoverRangeSquared != 10) {
            map.put("hr", hoverRangeSquared);
        }
        if (!frustum) {
            map.put("fe", false);
        }

        return map;
    }
}
