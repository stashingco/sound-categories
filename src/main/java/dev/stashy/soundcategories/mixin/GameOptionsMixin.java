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
    @Inject(at = @At(value = "HEAD"), method = "method_33667", cancellable = true)
    private static void getDefaultFloat(GameOptions.Visitor visitor, SoundCategory category, Float currentLevel, CallbackInfoReturnable<Float> cir)
    {
        float level;
        if (currentLevel == null && SoundCategories.defaultLevels.containsKey(category))
            level = SoundCategories.defaultLevels.get(category);
        else
            level = currentLevel != null ? currentLevel : 1.0F;
        cir.setReturnValue(visitor.visitFloat("soundCategory_" + category.getName(), level));
    }
}
