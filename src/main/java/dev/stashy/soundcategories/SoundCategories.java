package dev.stashy.soundcategories;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.sound.SoundCategory;

import java.util.HashMap;
import java.util.Map;

public class SoundCategories implements ClientModInitializer
{
    private static Map<String, RegisterCallback> categories = new HashMap<>();

    @Override
    public void onInitializeClient()
    {
    }

    public static void registerCategory(String name, RegisterCallback callback)
    {
        categories.put(name, callback);
    }

    public static Map<String, RegisterCallback> getCategories()
    {
        return categories;
    }

    public interface RegisterCallback
    {
        void apply(SoundCategory cat);
    }
}
