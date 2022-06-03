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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
    @Shadow
    @Final
    private Object2FloatMap<SoundCategory> soundVolumeLevels;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/sound/SoundCategory;values()[Lnet/minecraft/sound/SoundCategory;"), method = "accept")
    private void setDefaultFloats(GameOptions.Visitor visitor, CallbackInfo ci)
    {
        SoundCategories.defaultLevels.keySet().forEach(it -> {
            float f = visitor.visitFloat("soundCategory_" + it.getName(), SoundCategories.defaultLevels.get(it));
            soundVolumeLevels.put(it, f);
        });
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2FloatMap;computeFloat(Ljava/lang/Object;Ljava/util/function/BiFunction;)F"), method = "accept")
    float filterDefault(Object2FloatMap instance, Object key, BiFunction<? super Object, ? super Float, ? extends Float> remappingFunction)
    {
        if (!SoundCategories.defaultLevels.containsKey(key)) instance.computeFloat(key, remappingFunction);
        return 0;
    }

    @Inject(at = @At(value = "HEAD"), method = "getSoundVolume", cancellable = true)
    void getSoundVolume(SoundCategory category, CallbackInfoReturnable<Float> cir)
    {
        if (SoundCategories.defaultLevels.containsKey(category) && !soundVolumeLevels.containsKey(category))
            cir.setReturnValue(SoundCategories.defaultLevels.get(category));
    }
}
