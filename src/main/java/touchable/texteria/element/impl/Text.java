package touchable.texteria.element.impl;

import lombok.NonNull;
import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class Text extends Element2D<Text> {
    private final Align align = Align.CENTER;
    private final WordWrap wordWrap = WordWrap.NO_WRAP;
    private String[] text;
    private int width = -1;
    private boolean shadow = true;
    private int background = -1;
    private int hoverBackground = -1;

    public Text(String id, String... text) {
        super(id);
        this.text = text;
    }

    public Text setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    public Text setHoverBackground(int hoverBackground) {
        if (hoverBackground != -1) setHoverable(true);
        this.hoverBackground = hoverBackground;
        return this;
    }

    public String[] getText() {
        return this.text;
    }

    public Text setText(String @NonNull ... text) {
        this.text = text;
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public Text setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getBackground() {
        return this.background;
    }

    public Text setBackground(int background) {
        this.background = background;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);
        map.put("text", text);

        if (width != -1) {
            map.put("width", width);
        }
        if (align != Align.CENTER) {
            map.put("al", align.name());
        }
        if (wordWrap != WordWrap.NO_WRAP) {
            map.put("ww", wordWrap.name());
        }
        if (!shadow) {
            map.put("shadow", false);
        }
        if (background != -1) {
            map.put("bg", background);
        }
        if (hoverBackground != -1) {
            map.put("hoverBg", hoverBackground);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "Text";
    }

    public enum WordWrap {
        NO_WRAP, NORMAL, BREAK_WORD
    }

    public enum Align {
        LEFT, CENTER, RIGHT
    }
}

