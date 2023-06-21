package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.CategoryLoader;
import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.sound.SoundCategory;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@Mixin(SoundCategory.class)
public class SoundCategoryMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static SoundCategory newSoundCategory(String internalName, int internalId, String name) {
        throw new AssertionError();
    }

    //private final static synthetic [Lnet/minecraft/sound/SoundCategory; field_15255
    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    @Mutable
    private static SoundCategory[] field_15255;

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/sound/SoundCategory;field_15255:[Lnet/minecraft/sound/SoundCategory;",
            shift = At.Shift.AFTER))
    private static void addCustomVariants(CallbackInfo ci) {
        ArrayList<SoundCategory> categories = new ArrayList<>(Arrays.asList(field_15255));
        SoundCategories.getCategories().forEach((loader, fields) -> {
            fields.forEach(field -> {
                var annotation = field.getAnnotation(CategoryLoader.Register.class);
                var id = Objects.equals(annotation.id(), "") ? field.getName() : annotation.id();
                try {
                    field.set(loader, addVariant(categories, id));
                } catch (IllegalAccessException e) {
                    SoundCategories.LOGGER.warning("Failed to register sound category with ID " + id);
                    e.printStackTrace();
                }
            });
        });

        field_15255 = categories.toArray(SoundCategory[]::new);
    }

    private static SoundCategory addVariant(ArrayList<SoundCategory> categories, String name) {
        SoundCategory cat = newSoundCategory(name.toUpperCase(Locale.ROOT),
                categories.get(categories.size() - 1).ordinal() + 1,
                name.toLowerCase(Locale.ROOT));
        categories.add(cat);
        return cat;
    }
}
