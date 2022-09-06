package me.hugo.gadgetsmenu.util.gui;

public enum PageFormat {

    FIVE_ROWS("---------"
            + "-XXXXXXX-"
            + "-XXXXXXX-"
            + "-XXXXXXX-"
            + "-XXXXXXX-"
            + "-XXXXXXX-");

    private String format;

    PageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
