package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.gui.CustomSoundOptionsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OptionsScreen.class)
public class SoundSettingsMixin
{
    @Shadow
    @Final
    private GameOptions settings;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"), method = "method_19829")
    public void getSoundOptionsScreen(MinecraftClient instance, Screen screen)
    {
        instance.setScreen(new CustomSoundOptionsScreen((OptionsScreen) (Object) this, settings));
    }
}
