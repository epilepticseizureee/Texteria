package touchable.texteria.misc;

import touchable.texteria.element.Element2D;
import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class ClickData<E extends Element2D<E>> implements IWritable {
    private final Element2D<E> element;
    private String action;
    private Object data;

    public ClickData(Element2D<E> element) {
        this.element = element;
    }

    public Element2D<E> openURL(String url) {
        this.action = "URL";
        this.data = url;
        return element;
    }

    public Element2D<E> chatMessage(String chat) {
        this.action = "CHAT";
        this.data = chat;
        return element;
    }

    public Element2D<E> callback(ByteMap callbackMap) {
        this.action = "CALLBACK";
        this.data = callbackMap;
        return element;
    }

    public Element2D<E> toElement() {
        return element;
    }

    @Override
    public ByteMap write(ByteMap map) {
        if (action != null) {
            ByteMap clickMap = new ByteMap();
            clickMap.put("act", action);

            if (data != null) {
                clickMap.put("data", data);
            }

            map.put("click", clickMap);
        }

        return map;
    }
}

