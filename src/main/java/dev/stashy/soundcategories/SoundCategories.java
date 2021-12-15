package dev.stashy.soundcategories;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.sound.SoundCategory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SoundCategories
{
    private static Map<String, RegisterCallback> categories = new HashMap<>();

    public static void initCategories()
    {
        FabricLoader.getInstance().getEntrypoints("sound-categories", CategoryLoader.class)
                    .forEach(entry -> {
                        Arrays.stream(entry.getClass().getDeclaredFields())
                              .filter((f) -> f.isAnnotationPresent(CategoryLoader.Register.class))
                              .forEach((it) -> {
                                  String name = it.getAnnotation(CategoryLoader.Register.class).name();
                                  if (Objects.equals(name, ""))
                                      name = it.getName();
                                  categories.put(name, cat -> {
                                      try
                                      {
                                          it.set(entry, cat);
                                      }
                                      catch (IllegalAccessException e)
                                      {
                                          e.printStackTrace();
                                      }
                                  });
                              });
                    });
    }

    public static Map<String, RegisterCallback> getCategories()
    {
        return categories;
    }

    public interface RegisterCallback
    {
        void apply(SoundCategory cat);
    }
}
