package dev.stashy.soundcategories.mixin.v1_19;

import dev.stashy.soundcategories.v1_19.SCOptionsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OptionsScreen.class)
public abstract class SoundSettingsMixin extends Screen {

    @Shadow
    @Final
    private GameOptions settings;

    protected SoundSettingsMixin(Text title) {
        super(title);
    }

    @Redirect(method = "method_19829", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
    public void getSoundOptionsScreen(MinecraftClient instance, Screen screen) {
        instance.setScreen(new SCOptionsScreen(screen, settings));
    }
}
