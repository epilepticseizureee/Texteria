package touchable.texteria.element.impl;

@SuppressWarnings("unused")
public class TextStopwatch extends Text {
    public TextStopwatch(String id, String... lines) {
        super(id, lines);
    }

    @Override
    public String getElementType() {
        return "TextStopwatch";
    }
}

