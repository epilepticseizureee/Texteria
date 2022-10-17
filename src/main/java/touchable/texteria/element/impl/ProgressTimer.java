package touchable.texteria.element.impl;

import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class ProgressTimer extends ProgressBar {
    private boolean reverse;

    public ProgressTimer(String id, float width, float height) {
        super(id, width, height);
    }

    public ProgressTimer setReverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (reverse) {
            map.put("reverse", true);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "ProgressTimer";
    }
}

