package touchable.texteria.element.impl;

import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class Button extends Rectangle {
    private String text;
    private int hoverColor = -1;
    private int textColor = -1;

    public Button(String id, float width, float height, String text) {
        super(id, width, height);
        this.text = text;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public Button setHoverColor(int color) {
        this.hoverColor = color;
        return this;
    }

    public Button setTextColor(int color) {
        this.textColor = color;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);
        map.put("t", text);

        if (hoverColor != -1) {
            map.put("hc", hoverColor);
        }
        if (textColor != -1) {
            map.put("tc", textColor);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "Button";
    }
}

