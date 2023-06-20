package dev.stashy.soundcategories.mixin.versioned;

import dev.stashy.soundcategories.gui.CustomSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class SoundSettingsMixin_1_19_3 {
    @Shadow
    @Final
    private GameOptions settings;

    @Shadow
    protected abstract ButtonWidget createButton(Text message, Supplier<Screen> screenSupplier);

    @Shadow
    @Final
    private static Text SOUNDS_TEXT;

    @Redirect(at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;createButton(Lnet/minecraft/text/Text;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/widget/ButtonWidget;"), method = "init")
    public ButtonWidget getSoundOptionsScreen(OptionsScreen instance, Text message, Supplier<Screen> screenSupplier) {
        return this.createButton(SOUNDS_TEXT, () -> new CustomSoundOptionsScreen((OptionsScreen) (Object) this, this.settings));
    }
}
