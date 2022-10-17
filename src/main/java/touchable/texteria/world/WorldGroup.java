package touchable.texteria.world;

import com.google.common.collect.Lists;
import touchable.texteria.element.Element2D;
import touchable.texteria.element.Element3D;
import touchable.texteria.misc.Animation3D;
import touchable.texteria.util.ByteMap;
import touchable.texteria.util.Easing;

import java.util.List;

@SuppressWarnings("unused")
public class WorldGroup extends Element3D<WorldGroup> {
    public Animation3D animation;
    public List<Element2D<?>> elements = Lists.newLinkedList();
    private String id;
    private float x;
    private float y;
    private float z;
    private float angleX;
    private float angleY;
    private float angleZ;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private final long duration = -1L;
    private final int fadeStart = 255;
    private final int fadeFinish = 255;
    private boolean culling;
    private boolean adjustableAngle;
    private boolean centered;
    private boolean depth;

    public WorldGroup(String id) {
        super(id);
    }

    @Override
    public String getElementType() {
        return "group";
    }

    public void add(Element2D<?> element) {
        elements.add(element);
    }

    public WorldGroup setX(float x) {
        this.x = x;
        return this;
    }

    public WorldGroup setY(float y) {
        this.y = y;
        return this;
    }

    public WorldGroup setZ(float z) {
        this.z = z;
        return this;
    }

    public WorldGroup setAngleX(float x) {
        this.angleX = x;
        return this;
    }

    public WorldGroup setAngleY(float y) {
        this.angleY = y;
        return this;
    }

    public WorldGroup setAngleZ(float z) {
        this.angleZ = z;
        return this;
    }

    public WorldGroup setAngle(float angle) {
        this.angleX = this.angleY = this.angleZ = angle;
        return this;
    }

    public WorldGroup setScaleX(float x) {
        this.scaleX = x;
        return this;
    }

    public WorldGroup setScaleY(float y) {
        this.scaleY = y;
        return this;
    }

    public WorldGroup setScaleZ(float z) {
        this.scaleZ = z;
        return this;
    }

    public WorldGroup setScale(float scale) {
        this.scaleX = this.scaleY = this.scaleZ = scale;
        return this;
    }

    public WorldGroup setAdjustableAngle(boolean adjustableAngle) {
        this.adjustableAngle = adjustableAngle;
        return this;
    }

    public WorldGroup setCentered(boolean centered) {
        this.centered = centered;
        return this;
    }

    public WorldGroup setDepth(boolean depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (animation != null) {
            if (animation.editDuration != 255) {
                map.put("anim.editD", animation.editDuration);
            }
            if (animation.editEasing != Easing.easeInSine) {
                map.put("anim.editEasing", animation.editEasing);
            }
            if (animation.start != null) {
                map.put("anim.s", animation.start.serialize());
            }
            if (animation.finish != null) {
                map.put("anim.f", animation.finish.serialize());
            }
        }

        if (x != 0.0f || y != 0.0f || z != 0.0f) {
            if (x == y && x == y && x == z) {
                map.put("loc", x);
            } else {
                if (x != 0.0f) {
                    map.put("loc.x", x);
                }
                if (y != 0.0f) {
                    map.put("loc.y", y);
                }
                if (z != 0.0f) {
                    map.put("loc.z", z);
                }
            }
        }

        if (angleX != 0.0f || angleY != 0.0f || angleZ != 0.0f) {
            if (angleX == angleY && angleX == angleY && angleX == angleZ) {
                map.put("angle", angleX);
            } else {
                if (angleX != 0.0f) {
                    map.put("angle.x", angleX);
                }
                if (angleY != 0.0f) {
                    map.put("angle.y", angleY);
                }
                if (angleZ != 0.0f) {
                    map.put("angle.z", angleZ);
                }
            }
        }

        if (scaleX != 0.0f || scaleY != 0.0f || scaleZ != 0.0f) {
            if (scaleX == scaleY && scaleX == scaleY && scaleX == scaleZ) {
                map.put("scale", scaleX);
            } else {
                if (scaleX != 0.0f) {
                    map.put("scale.x", scaleX);
                }
                if (scaleY != 0.0f) {
                    map.put("scale.y", scaleY);
                }
                if (scaleZ != 0.0f) {
                    map.put("scale.z", scaleZ);
                }
            }
        }

        if (culling) {
            map.put("culling", true);
        }
        if (adjustableAngle) {
            map.put("adjAngle", true);
        }
        if (centered) {
            map.put("centered", true);
        }
        if (depth) {
            map.put("depth", this);
        }

        if (elements != null && !elements.isEmpty()) {
            ByteMap[] maps = new ByteMap[elements.size()];
            int i = 0;

            for (Element2D<?> element : elements) {
                maps[i++] = element.write(new ByteMap());
            }
            map.put("e", maps);
        }

        return map;
    }
}

