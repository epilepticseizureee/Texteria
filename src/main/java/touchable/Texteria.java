package touchable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import io.netty.channel.EventLoop;
import lombok.Getter;
import lombok.NonNull;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import touchable.texteria.events.TexteriaCallbackEvent;
import touchable.texteria.listeners.TexteriaListener;
import touchable.texteria.sound.TexteriaSound;
import touchable.texteria.util.ByteMap;

import java.util.*;

@SuppressWarnings("unused")
public class Texteria extends JavaPlugin implements PluginMessageListener {
    @Getter
    private static final Map<Player, Queue<byte[]>> buffer = Maps.newLinkedHashMap();

    @Getter
    private static Texteria instance;

    private final int nettyThreads = Integer.parseInt(System.getProperty("io.netty.eventLoopThreads", "0"));

    private final Map<EventLoop, List<Object[]>> sendQueueMap = Maps.newLinkedHashMap();
    private final LinkedList<Object[]> sendQueueList = Lists.newLinkedList();

    @Override
    public void onEnable() {
        instance = this;

        registerMessengers();
        registerListeners();

        for (Player player : Bukkit.getOnlinePlayers()) {
            new TexteriaListener().playerJoin(new PlayerJoinEvent(player, null));
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (buffer.isEmpty()) {
                return;
            }
            if (nettyThreads == 1) {
                sendQueueList.clear();
            } else {
                sendQueueMap.clear();
            }
            buffer.forEach((player, queue) -> {
                if (queue.isEmpty()) {
                    return;
                }
                int size = 2;
                List<byte[]> list = Lists.newArrayListWithCapacity(queue.size());
                byte[] temp = queue.poll();
                while (temp != null) {
                    list.add(temp);
                    size += 4 + temp.length;
                    temp = queue.poll();
                }

                PacketDataSerializer data = new PacketDataSerializer(Unpooled.buffer(size));
                data.b(list.size());
                for (byte[] arr : list) {
                    data.b(arr.length);
                    data.writeBytes(arr);
                }

                PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
                if (playerConnection != null) {
                    Object[] toSend = new Object[]{playerConnection, new PacketPlayOutCustomPayload("Texteria", data)};
                    if (nettyThreads == 1) {
                        sendQueueList.add(toSend);
                    } else {
                        sendQueueMap.computeIfAbsent(playerConnection.networkManager.channel.eventLoop(), l -> Lists.newLinkedList()).add(toSend);
                    }
                }
            });
            if (nettyThreads == 1) {
                if (sendQueueList.isEmpty()) {
                    return;
                }
                EventLoop loopGroup = ((PlayerConnection) sendQueueList.getFirst()[0]).networkManager.channel.eventLoop();
                loopGroup.execute(() -> sendQueueList.forEach(data -> ((PlayerConnection) data[0]).sendPacket((Packet<?>) data[1])));
                sendQueueList.clear();
            } else {
                if (sendQueueMap.isEmpty()) {
                    return;
                }
                for (Map.Entry<EventLoop, List<Object[]>> entry : sendQueueMap.entrySet()) {
                    List<Object[]> value = entry.getValue();
                    entry.getKey().execute(() -> value.forEach(data -> ((PlayerConnection) data[0]).sendPacket((Packet<?>) data[1])));
                }
                sendQueueMap.clear();
            }
        }, 1L, 1L);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    public void openUrl(String url, Player... players) {
        send(buildOpenUrl(url), players);
    }

    public void openUrl(String url, Collection<? extends Player> players) {
        send(buildOpenUrl(url), players);
    }

    public void setCmdCam(boolean value, Player... players) {
        send(buildCmdCam(value), players);
    }

    public void setCmdCam(boolean value, Collection<? extends Player> players) {
        send(buildCmdCam(value), players);
    }

    public void playSound(TexteriaSound sound, Player... players) {
        send(buildPlaySound(sound), players);
    }

    public void playSound(TexteriaSound sound, Collection<? extends Player> players) {
        send(buildPlaySound(sound), players);
    }

    public void stopSound(String soundId, Player... players) {
        send(buildStopSound(soundId), players);
    }

    public void stopSound(String soundId, Collection<? extends Player> players) {
        send(buildStopSound(soundId), players);
    }

    public void setDisableTab(boolean value, Player... players) {
        send(buildDisableTab(value), players);
    }

    public void setDisableTab(boolean value, Collection<? extends Player> players) {
        send(buildDisableTab(value), players);
    }

    public void setWorldLightUpdates(boolean value, Player... players) {
        send(buildWorldLightUpdates(value), players);
    }

    public void setWorldLightUpdates(boolean value, Collection<? extends Player> players) {
        send(buildWorldLightUpdates(value), players);
    }

    public void setAutumnColors(boolean value, Player... players) {
        send(buildAutumnColors(value), players);
    }

    public void setAutumnColors(boolean value, Collection<? extends Player> players) {
        send(buildAutumnColors(value), players);
    }

    public void setWorldLightForced(int worldLightForced, Player... players) {
        send(buildWorldLightForced(worldLightForced), players);
    }

    public void setWorldLightForced(int worldLightForced, Collection<? extends Player> players) {
        send(buildWorldLightForced(worldLightForced), players);
    }

    public void setDisableCpsLimit(boolean value, Player... players) {
        send(buildDisableCpsLimit(value), players);
    }

    public void setDisableCpsLimit(boolean value, Collection<? extends Player> players) {
        send(buildDisableCpsLimit(value), players);
    }

    public void setPlayerCollisionVelocity(int value, Player... players) {
        send(buildPlayerCollisionVelocity(value), players);
    }

    public void setPlayerCollisionVelocity(int value, Collection<? extends Player> players) {
        send(buildPlayerCollisionVelocity(value), players);
    }

    public void setPlayerCollisionBoxes(int value, Player... players) {
        send(buildPlayerCollisionBoxes(value), players);
    }

    public void setPlayerCollisionBoxes(int value, Collection<? extends Player> players) {
        send(buildPlayerCollisionBoxes(value), players);
    }

    public void setDisableClouds(boolean value, Player... players) {
        send(buildDisableClouds(value), players);
    }

    public void setDisableClouds(boolean value, Collection<? extends Player> players) {
        send(buildDisableClouds(value), players);
    }

    public void setCmdCamTeleport(boolean value, Player... players) {
        send(buildCmdCamTp(value), players);
    }

    public void setCmdCamTeleport(boolean value, Collection<? extends Player> players) {
        send(buildCmdCamTp(value), players);
    }

    private byte[] buildOpenUrl(String url) {
        ByteMap map = new ByteMap();
        map.put("%", "url");
        map.put("url", url);
        return map.toByteArray();
    }

    private byte[] buildCmdCamTp(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "cmdcam-tp");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildPlayerCollisionVelocity(int value) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "player-collision-velocity");
        map.put("value", value);
        return map.toByteArray();
    }

    private byte[] buildPlayerCollisionBoxes(int value) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "player-collision-boxes");
        map.put("value", value);
        return map.toByteArray();
    }

    private byte[] buildDisableClouds(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "disable-clouds");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildPlaySound(@NonNull TexteriaSound sound) {
        ByteMap map = new ByteMap();
        map.put("%", "sound:play");
        return sound.write(map).toByteArray();
    }

    private byte[] buildStopSound(String soundId) {
        ByteMap map = new ByteMap();
        map.put("%", "sound:stop");
        map.put("id", soundId);
        return map.toByteArray();
    }

    private byte[] buildDisableCpsLimit(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "disable-cps-limit");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildCmdCam(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "cmdcam");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildDisableTab(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "disable-tab");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildWorldLightUpdates(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "world-light-updates");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildAutumnColors(boolean enabled) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "autumn-colors");
        map.put("value", enabled);
        return map.toByteArray();
    }

    private byte[] buildWorldLightForced(int worldLightForced) {
        ByteMap map = new ByteMap();
        map.put("%", "option:set");
        map.put("field", "world-light-forced");
        map.put("value", worldLightForced);
        return map.toByteArray();
    }

    public void send(byte[] bytes, @NonNull Collection<? extends Player> players) {
        players.forEach(player -> {
            Queue<byte[]> queue = buffer.get(player);
            if (queue != null) {
                queue.add(bytes);
            }
        });
    }

    public void send(byte[] bytes, Player @NotNull [] players) {
        for (Player player : players) {
            Queue<byte[]> queue = buffer.get(player);
            if (queue == null) continue;
            queue.add(bytes);
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new TexteriaListener(), this);
    }

    private void registerMessengers() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Texteria");
        getServer().getMessenger().registerIncomingPluginChannel(this, "Texteria", this);
    }

    public void onPluginMessageReceived(@NonNull String channel, Player player, byte[] data) {
        if (channel.equals("Texteria")) {
            ByteMap map = new ByteMap(data);
            if ("callback".equals(map.getString("%", ""))) {
                Bukkit.getPluginManager().callEvent(new TexteriaCallbackEvent(player, map.getMap("data")));
            }
        }
    }
}

