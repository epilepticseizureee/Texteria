package touchable.texteria.misc;

import touchable.texteria.util.ByteMap;
import touchable.texteria.util.Easing;

@SuppressWarnings("unused")
public class Animation2D {
    public Params start = null;
    public Params finish = null;
    public int editDuration = 255;
    public Easing easing = Easing.easeInSine;

    public Animation2D setStart(Params params) {
        this.start = params;
        return this;
    }

    public Animation2D setFinish(Params params) {
        this.finish = params;
        return this;
    }

    public Animation2D setBoth(Params params) {
        this.finish = this.start = params;
        return this;
    }

    public Animation2D setEasing(Easing easing) {
        this.easing = easing;
        return this;
    }

    public static class Params {
        public int x = 0;
        public int y = 0;
        public float scaleX = 0.0f;
        public float scaleY = 0.0f;
        public float rotate = 0.0f;
        public Easing easing = Easing.easeInSine;
        public int duration = 255;
        public int color;
        public Params next;
        public boolean cyclic;

        public Params setX(int x) {
            this.x = x;
            return this;
        }

        public Params setY(int y) {
            this.y = y;
            return this;
        }

        public Params setScale(float scale) {
            this.scaleX = this.scaleY = scale;
            return this;
        }

        public Params setScaleX(float scale) {
            this.scaleX = scale;
            return this;
        }

        public Params setScaleY(float scale) {
            this.scaleY = scale;
            return this;
        }

        public Params setRotate(float rotate) {
            this.rotate = rotate;
            return this;
        }

        public Params setEasing(Easing easing) {
            this.easing = easing;
            return this;
        }

        public Params setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Params setColor(int color) {
            this.color = color;
            return this;
        }

        public Params setNext(Params next) {
            this.next = next;
            return this;
        }

        public Params setCyclic(boolean cyclic) {
            this.cyclic = cyclic;
            return this;
        }

        public ByteMap serialize() {
            ByteMap map = new ByteMap();

            if (x != 0) {
                map.put("x", x);
            }
            if (y != 0) {
                map.put("y", y);
            }
            if (scaleX != 0.0f || this.scaleY != 0.0f) {
                if (this.scaleX == this.scaleY) {
                    map.put("scale", scaleX);
                } else {
                    if (this.scaleX != 0.0f) {
                        map.put("scale.x", scaleX);
                    }
                }

                if (this.scaleY != 0.0f) {
                    map.put("scale.y", scaleY);
                }
            }

            if (rotate != 0.0f) {
                map.put("rot", rotate);
            }
            if (color != 0) {
                map.put("c", color);
            }
            if (easing != Easing.easeInSine) {
                map.put("easing", easing.name());
            }
            if (next != null) {
                map.put("next", next.serialize());
            }
            if (cyclic) {
                map.put("cyclic", true);
            }

            return map;
        }
    }
}

