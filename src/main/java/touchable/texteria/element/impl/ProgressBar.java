package touchable.texteria.element.impl;

import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class ProgressBar extends Rectangle {
    private float progress = 0.5f;
    private int barColor;
    private int borderColor = -1;

    public ProgressBar(String id, float width, float height) {
        super(id, width, height);
    }

    public ProgressBar setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    public ProgressBar setBarColor(int barColor) {
        this.barColor = barColor;
        return this;
    }

    public ProgressBar setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (progress != 0.5f) {
            map.put("progress", progress);
        }
        if (barColor != 0) {
            map.put("barColor", barColor);
        }
        if (borderColor != -1) {
            map.put("border", borderColor);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "ProgressBar";
    }
}

