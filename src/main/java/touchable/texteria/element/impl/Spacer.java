package touchable.texteria.element.impl;

import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class Spacer extends Element2D<Spacer> {
    private float width = 1.0f;
    private float height = 1.0f;

    public Spacer(String id) {
        super(id);
    }

    public Spacer setWidth(float width) {
        this.width = width;
        return this;
    }

    public Spacer setHeight(float height) {
        this.height = height;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (width != 1.0f) {
            map.put("width", width);
        }
        if (height != 1.0f) {
            map.put("height", height);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "Spacer";
    }
}
