package dev.stashy.soundcategories;

import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SCUtil {
    public static <T> List<Pair<T, T>> pairElements(List<T> elements) {
        List<Pair<T, T>> pairs = new ArrayList<>();
        int size = elements.size();
        for (int i = 0; i < size; i += 2) {
            pairs.add(new Pair<T, T>(elements.get(i), i + 1 < size ? elements.get(i + 1) : null));
        }
        return pairs;
    }
}
