package touchable.texteria.element.impl;

import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;
import touchable.texteria.util.Fluidity;

@SuppressWarnings("unused")
public class Rectangle extends Element2D<Rectangle> {
    private float width;
    private float height;

    public Rectangle(String id, float width, float height) {
        super(id);
        this.width = width;
        this.height = height;
    }

    public Rectangle setWidth(float width) {
        this.width = width;
        return this;
    }

    public Rectangle setHeight(float height) {
        this.height = height;
        return this;
    }

    public Rectangle setWidthFluidity(Fluidity fluidity) {
        switch (fluidity) {
            case FIXED: {
                if (width == -1 || width == -2) {
                    width = 0;
                }
                break;
            }
            case WRAP_CONTENT: {
                width = -1;
                break;
            }
            case MATCH_PARENT: {
                width = -2;
                break;
            }
        }
        return this;
    }

    public Rectangle setHeightFluidity(Fluidity fluidity) {
        switch (fluidity) {
            case FIXED: {
                if (height == -1 || height == -2) {
                    height = 0;
                }
                break;
            }
            case WRAP_CONTENT: {
                height = -1;
                break;
            }
            case MATCH_PARENT: {
                height = -2;
                break;
            }
        }
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (width == height) {
            map.put("size", width);
        } else {
            map.put("width", width);
        }

        map.put("height", height);

        return map;
    }

    @Override
    public String getElementType() {
        return "Rectangle";
    }
}

