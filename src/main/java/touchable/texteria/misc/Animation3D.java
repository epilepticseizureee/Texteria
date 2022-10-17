package touchable.texteria.misc;

import touchable.texteria.util.ByteMap;
import touchable.texteria.util.Easing;

@SuppressWarnings("unused")
public class Animation3D {
    public Params start = null;
    public Params finish = null;
    public int editDuration = 255;
    public Easing editEasing = Easing.easeInSine;

    public Animation3D setStart(Params params) {
        this.start = params;
        return this;
    }

    public Animation3D setFinish(Params params) {
        this.finish = params;
        return this;
    }

    public Animation3D setBoth(Params params) {
        this.finish = this.start = params;
        return this;
    }

    public Animation3D setDuration(int duration) {
        this.editDuration = duration;
        return this;
    }

    public Animation3D setEasing(Easing easing) {
        this.editEasing = easing;
        return this;
    }

    public static class Params {
        public float x;
        public float y;
        public float z;
        public float scaleX;
        public float scaleY;
        public float scaleZ;
        public float angleX;
        public float angleY;
        public float angleZ;

        public Params setX(float x) {
            this.x = x;
            return this;
        }

        public Params setY(float y) {
            this.y = y;
            return this;
        }

        public Params setZ(float z) {
            this.z = z;
            return this;
        }

        public Params setScale(float scale) {
            this.scaleX = this.scaleY = this.scaleZ = scale;
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

        public Params setScaleZ(float scale) {
            this.scaleZ = scale;
            return this;
        }

        public Params setAngleX(float x) {
            this.angleX = x;
            return this;
        }

        public Params setAngleY(float y) {
            this.angleY = y;
            return this;
        }

        public Params setAngleZ(float z) {
            this.angleZ = z;
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

            if (scaleX != 0.0f || this.scaleY != 0.0f || this.scaleZ != 0.0f) {
                if (this.scaleX == this.scaleY && this.scaleX == this.scaleZ && this.scaleY == this.scaleZ) {
                    map.put("scale", scaleX);
                } else {
                    if (this.scaleX != 0.0f) {
                        map.put("scale.x", scaleX);
                    }
                }
                if (this.scaleY != 0.0f) {
                    map.put("scale.y", scaleY);
                }
                if (this.scaleZ != 0.0f) {
                    map.put("scale.z", scaleZ);
                }
            }

            if (this.angleX != 0.0f) {
                map.put("angle.x", angleX);
            }
            if (this.angleY != 0.0f) {
                map.put("angle.y", angleY);
            }
            if (this.angleZ != 0.0f) {
                map.put("angle.z", angleZ);
            }

            return map;
        }
    }
}

