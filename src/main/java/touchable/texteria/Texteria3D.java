package touchable.texteria;

import lombok.NonNull;
import org.bukkit.entity.Player;
import touchable.Texteria;
import touchable.texteria.element.Element3D;
import touchable.texteria.util.ByteMap;
import touchable.texteria.world.WorldGroup;

import java.util.Collection;

@SuppressWarnings("unused")
public class Texteria3D {
    public static void addGroup(WorldGroup group, Player... players) {
        Texteria.getInstance().send(Texteria3D.packetAddGroup(group), players);
    }

    public static void addGroup(WorldGroup group, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria3D.packetAddGroup(group), players);
    }

    public static byte[] removeAllPacket() {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm:all");
        return map.toByteArray();
    }

    public static byte[] packetAddGroup(@NonNull WorldGroup group) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:add");
        group.write(map);
        return map.toByteArray();
    }

    public static void addToGroup(String group, Element3D<?> element, Player... players) {
        Texteria.getInstance().send(Texteria3D.packetAddToGroup(group, element), players);
    }

    public static void addToGroup(String group, Element3D<?> element, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria3D.packetAddToGroup(group, element), players);
    }

    public static byte[] packetAddToGroup(String group, @NonNull Element3D<?> element) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:add:to");
        map.put("group", group);
        ByteMap els = new ByteMap();
        element.write(els);
        map.put("e", els);
        return map.toByteArray();
    }

    public static void editGroup(String group, ByteMap data, Player... players) {
        Texteria.getInstance().send(Texteria3D.packetEditGroup(group, data), players);
    }

    public static void editGroup(String group, ByteMap data, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria3D.packetEditGroup(group, data), players);
    }

    public static byte[] packetEditGroup(String group, ByteMap data) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:edit");
        map.put("group", group);
        map.put("data", data);
        return map.toByteArray();
    }

    public static void editElementInGroup(String group, String elem, ByteMap data, Player... players) {
        Texteria.getInstance().send(Texteria3D.packetEditElementInGroup(group, elem, data), players);
    }

    public static void editElementInGroup(String group, String elem, ByteMap data, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria3D.packetEditElementInGroup(group, elem, data), players);
    }

    public static byte[] packetEditElementInGroup(String group, String elem, ByteMap data) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:edit:in");
        map.put("group", group);
        map.put("id", elem);
        map.put("data", data);
        return map.toByteArray();
    }

    public static void removeGroup(String group, Player... players) {
        Texteria.getInstance().send(Texteria3D.packetRemoveGroup(group), players);
    }

    public static void removeGroup(String group, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria3D.packetRemoveGroup(group), players);
    }

    public static byte[] packetRemoveGroup(String group) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm");
        map.put("group", group);
        return map.toByteArray();
    }

    public static void removeFromGroup(String group, String element, Player... players) {
        Texteria.getInstance().send(Texteria3D.packetRemoveFromGroup(group, element), players);
    }

    public static void removeFromGroup(String group, String element, Collection<? extends Player> players) {
        Texteria.getInstance().send(Texteria3D.packetRemoveFromGroup(group, element), players);
    }

    public static byte[] packetRemoveFromGroup(String group, String element) {
        ByteMap map = new ByteMap();
        map.put("%", "3d:rm:from");
        map.put("group", group);
        map.put("id", element);
        return map.toByteArray();
    }

    public static void removeAllGroups(Player... players) {
        Texteria.getInstance().send(removeAllPacket(), players);
    }

    public static void removeAllGroups(Collection<? extends Player> players) {
        Texteria.getInstance().send(removeAllPacket(), players);
    }
}

