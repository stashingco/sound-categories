package dev.stashy.soundcategories;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundCategories
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static Map<String, RegisterCallback> getCallbacks()
    {
        Map<String, RegisterCallback> categories = new HashMap<>();
        FabricLoader.getInstance().getEntrypoints("sound-categories", CategoryLoader.class)
                    .forEach(entry -> {
                        Arrays.stream(entry.getClass().getDeclaredFields())
                              .filter((f) -> f.isAnnotationPresent(CategoryLoader.Register.class))
                              .forEach((it) -> {
                                  String id = Objects.equals(it.getAnnotation(CategoryLoader.Register.class).id(),
                                                             "")
                                          ? it.getName() : it.getAnnotation(CategoryLoader.Register.class).id();
                                  categories.put(id, cat -> {
                                      try
                                      {
                                          it.set(entry, cat);
                                      }
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

    public interface RegisterCallback
    {
        void apply(SoundCategory cat);
    }
}
