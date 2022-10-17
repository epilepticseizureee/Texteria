package touchable.texteria;

import lombok.NonNull;
import org.bukkit.entity.Player;
import touchable.Texteria;
import touchable.texteria.element.Element2D;
import touchable.texteria.misc.Visibility;
import touchable.texteria.util.ByteMap;

import java.util.Collection;

@SuppressWarnings("unused")
public class Texteria2D {
    public static void add(Element2D<?> element, Player... players) {
        Texteria.getInstance().send(Texteria2D.packetAdd(element), players);
    }

    public static void add(Element2D<?> element, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria2D.packetAdd(element), players);
    }

    public static byte[] packetAdd(@NonNull Element2D<?> element) {
        ByteMap map = new ByteMap();
        map.put("%", "add");
        element.write(map);
        return map.toByteArray();
    }

    public static byte[] removeAllPacket() {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm:all");
        return map.toByteArray();
    }

    public static void add(Element2D<?>[] elements, Player... players) {
        Texteria.getInstance().send(Texteria2D.packetAddBulk(elements), players);
    }

    public static void add(Element2D<?>[] elements, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria2D.packetAddBulk(elements), players);
    }

    public static byte[] packetAddBulk(Element2D<?> @NonNull [] elements) {
        ByteMap map = new ByteMap();
        map.put("%", "add:bulk");
        ByteMap[] e = new ByteMap[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            e[i] = new ByteMap();
            elements[i].write(e[i]);
        }
        map.put("e", e);
        return map.toByteArray();
    }

    public static void add(Visibility vis, Element2D<?>[] elements, Player... players) {
        Texteria.getInstance().send(Texteria2D.packetAddGroup(vis, elements), players);
    }

    public static void add(Visibility vis, Element2D<?>[] elements, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria2D.packetAddGroup(vis, elements), players);
    }

    public static byte[] packetAddGroup(Visibility vis, Element2D<?> @NonNull [] elements) {
        ByteMap map = new ByteMap();
        map.put("%", "add:group");
        ByteMap[] e = new ByteMap[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            elements[i].setVisibility(null);
            e[i] = new ByteMap();
            elements[i].write(e[i]);
        }
        map.put("e", e);
        if (vis != null) {
            map.put("vis", vis.getSerialized());
        }
        return map.toByteArray();
    }

    public static void edit(String id, ByteMap data, Player... players) {
        Texteria.getInstance().send(Texteria2D.packetEdit(id, data), players);
    }

    public static void edit(String id, ByteMap data, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria2D.packetEdit(id, data), players);
    }

    public static byte[] packetEdit(String id, ByteMap data) {
        ByteMap map = new ByteMap();
        map.put("%", "edit");
        map.put("id", id);
        map.put("data", data);
        return map.toByteArray();
    }

    public static void remove(String id, Player... players) {
        Texteria.getInstance().send(Texteria2D.packetRemove(id), players);
    }

    public static void remove(String id, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria2D.packetRemove(id), players);
    }

    public static byte[] packetRemove(String id) {
        ByteMap map = new ByteMap();
        map.put("%", "rm:id");
        map.put("id", id);
        return map.toByteArray();
    }

    public static void removeGroup(String group, Player... players) {
        Texteria.getInstance().send(Texteria2D.packetRemoveGroup(group), players);
    }

    public static void removeGroup(String group, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria2D.packetRemoveGroup(group), players);
    }

    public static byte[] packetRemoveGroup(String group) {
        ByteMap map = new ByteMap();
        map.put("%", "rm:group");
        map.put("group", group);
        return map.toByteArray();
    }

    public static void removeAll(Player... players) {
        Texteria.getInstance().send(removeAllPacket(), players);
    }

    public static void removeAll(Collection<? extends Player> players) {
        Texteria.getInstance().send(removeAllPacket(), players);
    }
}

