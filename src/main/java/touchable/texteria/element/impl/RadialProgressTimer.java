package touchable.texteria.element.impl;

@SuppressWarnings("unused")
public class RadialProgressTimer extends RadialProgressBar {
    public RadialProgressTimer(String id, int size) {
        super(id, size);
    }

    @Override
    public String getElementType() {
        return "RadialProgressTimer";
    }
}

