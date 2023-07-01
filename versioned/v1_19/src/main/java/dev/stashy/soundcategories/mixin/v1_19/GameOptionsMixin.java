package dev.stashy.soundcategories.mixin.v1_19;

import dev.stashy.soundcategories.SoundCategories;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Shadow
    @Final
    private Object2FloatMap<SoundCategory> soundVolumeLevels;

    @Inject(at = @At(value = "HEAD"), method = "load")
    private void setDefaultLevels(CallbackInfo ci) {
        for (SoundCategory cat : SoundCategories.defaultLevels.keySet()) {
            soundVolumeLevels.put(cat, SoundCategories.defaultLevels.get(cat).floatValue());
        }
    }
}
