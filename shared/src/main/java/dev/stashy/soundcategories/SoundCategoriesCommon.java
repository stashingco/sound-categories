package dev.stashy.soundcategories;

import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SoundCategoriesCommon {
    public static final String MODID = "soundcategories";

    public static final Map<SoundCategory, SoundCategory> parents = new HashMap<>();
    public static final Map<SoundCategory, Double> defaultLevels = new HashMap<>();

    public static final Identifier SETTINGS_ICON = Identifier.of(MODID, "textures/gui/settings.png");
}
