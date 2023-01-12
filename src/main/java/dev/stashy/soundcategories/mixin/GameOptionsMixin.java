package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
    @Unique
    private SoundCategory currentCategory = null;

    /**
     * Storing the current SoundCategory in loop.
     */
    @Inject(method = "createSoundVolumeOption", at = @At("HEAD"))
    private void soundcategories$storeCategory(String key, SoundCategory soundCategory, CallbackInfoReturnable<SimpleOption> cir) {
        currentCategory = soundCategory;
    }

    /**
     * Modifying a Constant of the default sound volume that exists in <code>SoundCategories.defaultLevels</code> and matches <code>this.currentCategory</code>.<br>
     * default value is 1.0.
     *
     * @see GameOptions#createSoundVolumeOption
     * @see GameOptions#GameOptions
     */
    @ModifyConstant(method = "createSoundVolumeOption", constant = @Constant(doubleValue = 1.0))
    private double soundcategories$changeDefault(double value)
    {
        if (currentCategory == null) {
            return value;
        }
        return SoundCategories.defaultLevels.getOrDefault(this.currentCategory, (float) value);
    }
}
