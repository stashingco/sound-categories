package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoundSystem.class)
public class SoundSystemMixin
{
    @Shadow
    @Final
    private GameOptions settings;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getSoundVolume(Lnet/minecraft/sound/SoundCategory;)F"), method = "getSoundVolume")
    public float adjustGroupVolume(GameOptions instance, SoundCategory category)
    {
        return settings.getSoundVolume(category)
                * (SoundCategories.parents.containsKey(category)
                ? settings.getSoundVolume(SoundCategories.parents.get(category))
                : 1f);
    }
}
