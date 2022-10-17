package touchable.texteria.element.impl;

import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class TextTimer extends Text {
    private long customTime = 0;

    public TextTimer(String id, String... text) {
        super(id, text);
    }

    public TextTimer setCustomTime(long customTime) {
        this.customTime = customTime;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (customTime != 0) {
            map.put("millis", customTime);
        }

        return map;
    }

    @Override
    public String getElementType() {
        return "TextTimer";
    }
}

