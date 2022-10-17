package touchable.texteria.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class TexteriaCallbackEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final ByteMap data;

    public TexteriaCallbackEvent(Player player, ByteMap data) {
        super(player);
        this.data = data;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public ByteMap getData() {
        return this.data;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }
}

