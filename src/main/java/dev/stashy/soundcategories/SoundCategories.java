package dev.stashy.soundcategories;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class SoundCategories
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static Map<String, RegisterCallback> getCallbacks()
    {
        Map<String, RegisterCallback> categories = new HashMap<>();
        FabricLoader.getInstance().getEntrypoints("sound-categories", CategoryLoader.class)
                    .forEach(entry -> {
                        getRegistrations(entry).forEach((it) -> {
                            String id = Objects.equals(it.getAnnotation(CategoryLoader.Register.class).id(), "")
                                    ? it.getName() : it.getAnnotation(CategoryLoader.Register.class).id();
                            categories.put(id, cat -> {
                                try {it.set(entry, cat);}
                                catch (IllegalAccessException e)
                                {
                                    LOGGER.error("Unable to register sound category with ID {}", id);
                                    e.printStackTrace();
                                }
                            });
                        });
                    });
        return categories;
    }

    private static Stream<Field> getRegistrations(CategoryLoader loader)
    {
        return Arrays.stream(loader.getClass().getDeclaredFields())
                     .filter(it -> it.isAnnotationPresent(CategoryLoader.Register.class));
    }

    public interface RegisterCallback
    {
        void apply(SoundCategory cat);
    }
}
