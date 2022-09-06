package me.hugo.gadgetsmenu.util.gui;

import com.google.common.base.Strings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public enum CustomUI {

    GADGETS_MENU("\uF003", 2);

    private final String character;
    private final int offset;

    CustomUI(String character, int offset) {
        this.character = character;
        this.offset = offset;
    }

    public Component getFinalTitle() {
        return Component.text(Strings.repeat("\u3201", this.offset)).append(Component.text(this.character).color(NamedTextColor.WHITE));
    }

}
