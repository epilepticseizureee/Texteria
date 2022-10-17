package touchable.texteria.buffer;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import touchable.texteria.Texteria2D;
import touchable.texteria.element.Element2D;
import touchable.texteria.misc.Visibility;

import java.util.List;

@SuppressWarnings("unused")
public class TexteriaBuffer {
    private final List<Element2D<?>> elements = Lists.newLinkedList();

    @Getter
    @Setter
    private boolean enabled = false;

    public void add(Element2D<?> element2D, Player... players) {
        if (isEnabled()) {
            elements.add(element2D);
        } else {
            Texteria2D.add(element2D, players);
        }
    }

    public void send(Player... players) {
        send(null, players);
    }

    public void send(Visibility vis, Player... players) {
        setEnabled(false);
        if (elements.isEmpty()) {
            return;
        }
        if (elements.size() == 1) {
            Texteria2D.add(elements.get(0).setVisibility(vis), players);
        } else {
            Texteria2D.add(vis, elements.toArray(new Element2D[0]), players);
        }
        elements.clear();
    }
}

