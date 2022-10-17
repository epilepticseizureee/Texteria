package touchable.texteria.world;

import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class Beam extends WorldGroup {
    public int color;

    public Beam(String id, int color) {
        super(id);
        this.color = color;
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);
        map.put("type", "beam");
        map.put("color", this.color);
        map.remove("e");
        return map;
    }
}

