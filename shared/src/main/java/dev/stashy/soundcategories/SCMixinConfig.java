package dev.stashy.soundcategories;

import dev.stashy.mixinswap.MixinSwap;
import dev.stashy.mixinswap.MixinSwapPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class SCMixinConfig extends MixinSwapPlugin {
    public String MODID;

    public SCMixinConfig(String modId) {
        MODID = modId;
    }

    @Override
    public void onLoad(String mixinPackage) {
        var loader = FabricLoader.getInstance();
        ModContainer modContainer = loader.getModContainer(MODID).orElseThrow();

        mixinClasses = MixinSwap.getMatchingMixins(mixinPackage, modContainer, loader);
        System.out.println("Loading mixins for " + MODID + ": " + String.join(", ", mixinClasses));
    }
}
