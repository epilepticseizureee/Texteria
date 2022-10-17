package touchable.texteria.element.impl;

import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class RadialProgressBar extends Element2D<RadialProgressBar> {
    private int size;
    private float progress = 0.5f;

    public RadialProgressBar(String id, int size) {
        super(id);
        this.size = size;
    }

    public RadialProgressBar setSize(int size) {
        this.size = size;
        return this;
    }

    public RadialProgressBar setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);
        map.put("size", size);

        if (progress != 0.5f) {
            map.put("progress", progress);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "RadialProgressBar";
    }
}

