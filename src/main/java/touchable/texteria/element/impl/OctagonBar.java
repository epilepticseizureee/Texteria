package touchable.texteria.element.impl;

import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;

@SuppressWarnings("all")
public class OctagonBar extends Element2D<OctagonBar> {
    private int size;
    private int progress = 4;
    private int fillColor;
    private int borderColor = -1;

    public OctagonBar(String id) {
        super(id);
    }

    public OctagonBar setSize(int size) {
        this.size = size;
        return this;
    }

    public OctagonBar setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public OctagonBar setFillColor(int fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public OctagonBar setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (size != 0) {
            map.put("size", size);
        }
        if (progress != 4) {
            map.put("progress", progress);
        }
        if (fillColor != 0) {
            map.put("fill", fillColor);
        }
        if (borderColor != -1) {
            map.put("border", borderColor);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "OctagonBar";
    }
}
