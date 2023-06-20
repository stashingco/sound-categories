package dev.stashy.soundcategories.mixin;

import dev.stashy.mixinswap.MixinSwap;
import dev.stashy.mixinswap.MixinSwapPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class SoundCategoryMixinConfig extends MixinSwapPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        var loader = FabricLoader.getInstance();
        ModContainer modContainer = loader.getModContainer("soundcategories").orElseThrow();

        mixinClasses = MixinSwap.getMatchingMixins(mixinPackage, modContainer, loader);
        System.out.println("Loading mixins: " + String.join(", ", mixinClasses));
    }
}
