package touchable.texteria.sound;

import touchable.texteria.util.ByteMap;
import touchable.texteria.misc.IWritable;
import touchable.texteria.util.PositionFloat;

@SuppressWarnings("unused")
public class TexteriaSound implements IWritable {
    private final String id;
    private final String path;
    public boolean repeat;
    public int repeatDelay;
    public float distance = 0.0f;
    private boolean streaming = false;
    private Category category = Category.AMBIENT;
    private float pitch = 1.0f;
    private float volume = 1.0f;
    private PositionFloat pos;

    public TexteriaSound(String id, String path) {
        this.id = id;
        this.path = path;
    }

    public TexteriaSound setStreaming(boolean streaming) {
        this.streaming = streaming;
        return this;
    }

    public TexteriaSound setCategory(Category category) {
        this.category = category;
        return this;
    }

    public TexteriaSound setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public TexteriaSound setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public TexteriaSound setRepeatDelay(int repeatDelay) {
        this.repeatDelay = repeatDelay;
        return this;
    }

    public TexteriaSound setRepeat(boolean repeat) {
        this.repeat = repeat;
        return this;
    }

    public TexteriaSound setLocation(PositionFloat pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public ByteMap write(ByteMap map) {
        map.put("id", id);
        map.put("path", path);

        if (streaming) {
            map.put("stream", true);
        }
        if (category != Category.AMBIENT) {
            map.put("category", category.getName());
        }

        if (pitch != 1.0f) {
            map.put("pitch", pitch);
        }

        if (volume != 1.0f) {
            map.put("volume", volume);
        }
        if (pos != null) {
            map.put("pos.x", pos.getX());
            map.put("pos.y", pos.getY());
            map.put("pos.z", pos.getZ());
        }
        if (repeat) {
            map.put("repeat", true);
        }
        if (repeatDelay != 0) {
            map.put("repDel", repeatDelay);
        }
        if (distance != 0.0f) {
            map.put("dist", distance);
        }
        return map;
    }

    public static enum Category {
        MASTER("master"),
        MUSIC("music"),
        RECORDS("record"),
        WEATHER("weather"),
        BLOCKS("block"),
        MOBS("hostile"),
        ANIMALS("neutral"),
        PLAYERS("player"),
        AMBIENT("ambient");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
