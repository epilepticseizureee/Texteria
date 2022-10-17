package touchable.texteria.element;

import touchable.texteria.misc.Animation2D;
import touchable.texteria.misc.Attachment;
import touchable.texteria.misc.ClickData;
import touchable.texteria.misc.Visibility;
import touchable.texteria.util.ByteMap;
import touchable.texteria.util.Easing;
import touchable.texteria.misc.IWritable;
import touchable.texteria.misc.Position;

@SuppressWarnings("all")
public abstract class Element2D<E extends Element2D<E>> implements IWritable {
    private final String id;
    private int color = -1;
    private float x = 0.0f;
    private float y = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float rotate = 0.0f;
    private Position position;
    private int delay = 0;
    private long duration = -1;
    private boolean hoverable;
    private Visibility visibility;
    private Animation2D animation;
    private Attachment attachment;
    private int fadeStart = 255;
    private int fadeFinish = 255;
    private ClickData<E> clickData;

    public Element2D(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public float getX() {
        return x;
    }

    public E setX(float x) {
        this.x = x;
        return (E) this;
    }

    public float getY() {
        return y;
    }

    public E setY(float y) {
        this.y = y;
        return (E) this;
    }

    public float getRotate() {
        return rotate;
    }

    public E setRotate(float rotate) {
        this.rotate = rotate;
        return (E) this;
    }

    public E setXY(float x, float y) {
        this.x = x;
        this.y = y;
        return (E) this;
    }

    public E setScaleX(float x) {
        this.scaleX = x;
        return (E) this;
    }

    public E setScaleY(float y) {
        this.scaleY = y;
        return (E) this;
    }

    public E setScaleXY(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
        return (E) this;
    }

    public E setScale(float xy) {
        this.scaleX = xy;
        this.scaleY = xy;
        return (E) this;
    }

    public E setDelay(int delay) {
        this.delay = delay;
        return (E) this;
    }

    public E setDuration(long duration) {
        this.duration = duration;
        return (E) this;
    }

    public E setPosition(Position position) {
        this.position = position;
        return (E) this;
    }

    public E setColor(int color) {
        this.color = color;
        return (E) this;
    }

    public E setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
        return (E) this;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    public E setVisibility(Visibility visibility) {
        this.visibility = visibility;
        return (E) this;
    }

    public E setAnimation(Animation2D animation) {
        this.animation = animation;
        return (E) this;
    }

    public E setFadeStart(int fade) {
        this.fadeStart = fade;
        return (E) this;
    }

    public E setFadeFinish(int fade) {
        this.fadeFinish = fade;
        return (E) this;
    }

    public E setFade(int fade) {
        this.fadeFinish = this.fadeStart = fade;
        return (E) this;
    }

    public E setAttachment(Attachment attachment) {
        this.attachment = attachment;
        return (E) this;
    }

    public ClickData<E> withClickData() {
        if (clickData == null) clickData = new ClickData<>(this);
        return clickData;
    }

    @Override
    public ByteMap write(ByteMap map) {
        map.put("type", getElementType());
        map.put("id", (id == null ? String.valueOf(System.currentTimeMillis()) : id));

        if (x != 0.0f) {
            map.put("x", x);
        }
        if (y != 0.0f) {
            map.put("y", y);
        }
        if (visibility != null) {
            map.put("vis", this.visibility.getSerialized());
        }
        if (rotate != 0.0f) {
            map.put("rot", rotate);
        }
        if (color != -1) {
            map.put("color", color);
        }

        if (scaleX != 1.0f || scaleY != 1.0f) {
            if (scaleX == scaleY) {
                map.put("scale", scaleX);
            } else {
                if (scaleX != 1.0f) {
                    map.put("scale.x", scaleX);
                }
                if (scaleY != 1.0f) {
                    map.put("scale.y", scaleY);
                }
            }
        }
        if (delay != 0) {
            map.put("delay", delay);
        }
        if (duration != -1) {
            map.put("dur", duration);
        }
        if (position != null) {
            map.put("pos", position.name());
        }

        if (attachment != null) {
            map.put("attach.to", attachment.attachTo);
            map.put("attach.loc", attachment.attachLocation.name());
            if (attachment.attachLocation != attachment.orientation) {
                map.put("attach.orient", attachment.orientation.name());
            }

            if (!attachment.removeWhenParentRemove) {
                map.put("attach.rwpr", false);
            }
        }
        if (hoverable) {
            map.put("hv", true);
        }

        if (animation != null) {
            if (animation.editDuration != 255) {
                map.put("anim.editD", animation.editDuration);
            }
            if (animation.easing != Easing.easeInSine) {
                map.put("anim.editEasing", animation.easing.name());
            }
            if (animation.start != null) {
                map.put("anim.s", animation.start.serialize());
            }
            if (animation.finish != null) {
                map.put("anim.f", animation.finish.serialize());
            }
        }
        if (fadeStart != 255 || fadeFinish != 255) {
            if (fadeStart == fadeFinish) {
                map.put("fade", fadeStart);
            } else {
                if (this.fadeStart != 255) {
                    map.put("fade.s", fadeStart);
                }
                if (this.fadeFinish != 255) {
                    map.put("fade.f", fadeFinish);
                }
            }
        }
        if (clickData != null) {
            map = clickData.write(map);
        }
        return map;
    }

    public abstract String getElementType();
}
