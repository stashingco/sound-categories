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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
    @Shadow
    @Final
    private Object2FloatMap<SoundCategory> soundVolumeLevels;

    @Inject(at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2FloatMap;computeFloat(Ljava/lang/Object;Ljava/util/function/BiFunction;)F"), method = "accept", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getDefaultFloat(GameOptions.Visitor visitor, CallbackInfo ci, SoundCategory var2[], int var3, int var4, SoundCategory keyBinding)
    {
        SoundCategories.defaultLevels.keySet().forEach(it -> {
            soundVolumeLevels.put(it, visitor.visitFloat("soundCategory_" + it.getName(),
                                                         SoundCategories.defaultLevels.get(it)));
        });
    }
}
