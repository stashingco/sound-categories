package dev.stashy.soundcategories;

public record SCGuiLayout(int x, int width) {

    public static SCGuiLayout flowLayout(int index, int count, int totalWidth, int margin) {
        int elementMargin = count <= 1 ? 0 : margin;
        int totalMargin = elementMargin * (count - 1);
        int elementWidth = totalWidth / count - totalMargin;

        return new SCGuiLayout(
                elementWidth + (elementMargin * index),
                elementWidth
        );
    }
}
