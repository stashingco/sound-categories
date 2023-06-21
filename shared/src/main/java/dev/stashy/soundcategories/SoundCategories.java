package dev.stashy.soundcategories;

import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundCategories {
    public static final String MODID = "soundcategories";

    public static final Map<SoundCategory, SoundCategory> parents = new HashMap<>();
    public static final Map<SoundCategory, Double> defaultLevels = new HashMap<>();

    public static final Identifier SETTINGS_ICON = Identifier.of(MODID, "textures/gui/settings.png");

    public static List<SoundCategory> getUnparentedCategories() {
        return Arrays.stream(SoundCategory.values())
                .filter(it -> !parents.containsKey(it) && !parents.containsValue(it))
                .skip(1).collect(Collectors.toList());
    }

    public static List<SoundCategory> getParentCategories() {
        return Arrays.stream(SoundCategory.values())
                .filter(parents::containsValue)
                .collect(Collectors.toList());
    }

    public static List<SoundCategory> getChildren(SoundCategory parent) {
        return Arrays.stream(SoundCategory.values())
                .filter(parents::containsKey)
                .filter(it -> parents.get(it) == parent)
                .collect(Collectors.toList());
    }
}
