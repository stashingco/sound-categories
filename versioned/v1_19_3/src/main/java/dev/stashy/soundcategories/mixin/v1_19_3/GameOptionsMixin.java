package dev.stashy.soundcategories.mixin.v1_19_3;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Shadow
    @Final
    private Map<SoundCategory, SimpleOption<Double>> soundVolumeLevels;

    @Inject(at = @At(value = "HEAD"), method = "load")
    private void setDefaultLevels(CallbackInfo ci) {
        for (SoundCategory cat : SoundCategories.defaultLevels.keySet()) {
            SimpleOption<Double> volume = soundVolumeLevels.get(cat);
            volume.setValue(SoundCategories.defaultLevels.get(cat));
        }
    }
}
