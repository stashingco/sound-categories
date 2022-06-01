package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public class GameOptionsMixin
{
    @Inject(at = @At(value = "HEAD"), method = "method_33667", cancellable = true, require = 0)
    private static void getDefaultFloat(GameOptions.Visitor visitor, SoundCategory category, Float currentLevel, CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(
                visitor.visitFloat(
                        "soundCategory_" + category.getName(),
                        currentLevel != null
                                ? currentLevel
                                : SoundCategories.defaultLevels.getOrDefault(category, 1.0F)
                )
        );
    }
}
