package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.SoundCategories;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.BiFunction;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
    @Shadow
    @Final
    private Object2FloatMap<SoundCategory> soundVolumeLevels;

    @Redirect(at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2FloatMap;computeFloat(Ljava/lang/Object;Ljava/util/function/BiFunction;)F"), method = "accept")
    private float removeComputeFloat(Object2FloatMap instance, Object key, BiFunction remappingFunction)
    {
        return 0;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2FloatMap;computeFloat(Ljava/lang/Object;Ljava/util/function/BiFunction;)F"), method = "accept", locals = LocalCapture.CAPTURE_FAILHARD)
    private void computeFloat(GameOptions.Visitor visitor, CallbackInfo ci, SoundCategory[] var2, int var3, int var4, SoundCategory soundCategory)
    {
        soundVolumeLevels.computeFloat(soundCategory, (category, currentLevel) -> visitor.visitFloat(
                "soundCategory_" + category.getName(),
                currentLevel != null ? currentLevel : SoundCategories.defaultLevels.getOrDefault(category, 1.0f)));
    }

    @Inject(at = @At(value = "HEAD"), method = "load")
    private void preLoad(CallbackInfo ci)
    {
        soundVolumeLevels.putAll(SoundCategories.defaultLevels);
    }
}
