package dev.stashy.soundcategories;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SoundCategories
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static Map<CategoryLoader, Stream<Field>> getCategories()
    {
        return FabricLoader.getInstance().getEntrypoints("sound-categories", CategoryLoader.class).stream()
                           .collect(Collectors.toMap(it -> it, SoundCategories::getRegistrations));
    }

    private static Stream<Field> getRegistrations(CategoryLoader loader)
    {
        return Arrays.stream(loader.getClass().getDeclaredFields())
                     .filter(it -> it.isAnnotationPresent(CategoryLoader.Register.class));
    }

    @Override
    public void onInitializeClient()
    {

    }
}
