package touchable.texteria.element.impl;

import touchable.texteria.element.Element2D;

public class Vignette extends Element2D<Vignette> {
    public Vignette(String id) {
        super(id);
    }

    @Override
    public String getElementType() {
        return "Vignette";
    }
}

