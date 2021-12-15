package dev.stashy.soundcategories;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.sound.SoundCategory;

import java.util.ArrayList;
import java.util.List;

public class SoundCategories implements ClientModInitializer
{
    private List<SoundCategory> categories = new ArrayList<>();

    @Override
    public void onInitializeClient()
    {
    }
}
