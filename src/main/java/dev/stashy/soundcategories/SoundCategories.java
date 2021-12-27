package dev.stashy.soundcategories;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundCategories implements ClientModInitializer
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Map<SoundCategory, SoundCategory> parents = new HashMap<>();

    public static final Identifier SETTINGS_ICON = new Identifier("soundcategories", "textures/gui/settings.png");

    public static Map<CategoryLoader, List<Field>> getCategories()
    {
        return FabricLoader.getInstance().getEntrypoints("sound-categories", CategoryLoader.class).stream()
                           .collect(Collectors.toMap(it -> it, SoundCategories::getRegistrations));
    }

    private static List<Field> getRegistrations(CategoryLoader loader)
    {
        return Arrays.stream(loader.getClass().getDeclaredFields())
                     .filter(it -> it.isAnnotationPresent(CategoryLoader.Register.class)).toList();
    }

    @Override
    public void onInitializeClient()
    {
        var init = SoundCategory.MASTER; //required so that the new categories are actually created, not actually used

        getCategories().forEach((loader, fields) -> {
            var masterField = fields.stream()
                                    .filter(it -> it.getAnnotation(CategoryLoader.Register.class).master()).findFirst();
            fields.forEach(field -> {
                if (masterField.isPresent())
                {
                    try
                    {
                        SoundCategory master = (SoundCategory) masterField.get().get(loader);
                        SoundCategory child = (SoundCategory) field.get(loader);
                        if (master != child)
                            parents.put(child, master);
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}
