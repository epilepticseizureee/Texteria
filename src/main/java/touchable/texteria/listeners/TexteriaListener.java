package touchable.texteria.listeners;

import com.google.common.collect.Queues;
import lombok.NonNull;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import touchable.Texteria;
import touchable.texteria.Texteria2D;
import touchable.texteria.Texteria3D;

public class TexteriaListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoin(@NonNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Texteria.getBuffer().put(player, Queues.newConcurrentLinkedQueue());

        ((CraftPlayer) player).addChannel("Texteria");

        Texteria2D.removeAll(player);
        Texteria3D.removeAllGroups(player);
    }

    @EventHandler
    public void onPlayerQuit(@NonNull PlayerQuitEvent event) {
        Texteria.getBuffer().remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(@NonNull PlayerKickEvent event) {
        Texteria.getBuffer().remove(event.getPlayer());
    }
}
