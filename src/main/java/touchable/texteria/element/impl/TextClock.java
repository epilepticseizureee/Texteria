package touchable.texteria.element.impl;

import lombok.NonNull;
import touchable.texteria.util.ByteMap;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@SuppressWarnings("all")
public class TextClock extends Text {
    private final TimeZone tz;
    private final SimpleDateFormat dateFormat;
    private final int width = -1;
    private final long offsetMilliseconds = -1L;

    public TextClock(String id, SimpleDateFormat dateFormat, TimeZone tz) {
        super(id);
        this.dateFormat = dateFormat;
        this.tz = tz;
    }


    @Override
    public ByteMap write(@NonNull ByteMap map) {
        map.put("tz", tz.getID());

        if (this.width != -1) {
            map.put("width", this.width);
        }

        if (this.offsetMilliseconds != -1L) {
            map.put("st", this.offsetMilliseconds);
        }

        return super.write(map);
    }

    @Override
    public String getElementType() {
        return "TextClock";
    }
}
