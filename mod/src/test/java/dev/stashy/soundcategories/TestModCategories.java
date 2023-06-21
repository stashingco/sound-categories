package dev.stashy.soundcategories;

import net.minecraft.sound.SoundCategory;

public class TestModCategories implements CategoryLoader {
    @CategoryLoader.Register(master = true)
    public static SoundCategory TEST_MASTER;
    @CategoryLoader.Register()
    public static SoundCategory TEST_CHILD1;
    @CategoryLoader.Register()
    public static SoundCategory TEST_CHILD2;
    @CategoryLoader.Register(defaultLevel = 0.5)
    public static SoundCategory TEST_CHILD_50PERCENT;
}
