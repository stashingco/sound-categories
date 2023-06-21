package dev.stashy.soundcategories.mixin.versioned;

import dev.stashy.soundcategories.SoundCategories;
import dev.stashy.soundcategories.SoundCategoriesPreLaunch;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin_1_19_3 {
    @Shadow
    @Final
    private Map<SoundCategory, Object> soundVolumeLevels;

    @Inject(at = @At(value = "HEAD"), method = "load")
    private void setDefaultLevels(CallbackInfo ci) {
        try {
            for (SoundCategory cat : SoundCategories.defaultLevels.keySet()) {
                Object soundVolumeSimpleOption = soundVolumeLevels.get(cat);
                Double level = SoundCategories.defaultLevels.get(cat);
                var setValueMethod = Class.forName("net.minecraft.client.option.SimpleOption").getMethod("setValue", Object.class);
                setValueMethod.invoke(soundVolumeSimpleOption, level);
            }
        } catch (Exception e) {
            SoundCategoriesPreLaunch.LOGGER.severe("Failed loading volume levels.");
            e.printStackTrace();
        }
    }
}
