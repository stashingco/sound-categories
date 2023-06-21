package dev.stashy.soundcategories;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.sound.SoundCategory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static dev.stashy.soundcategories.SoundCategories.defaultLevels;
import static dev.stashy.soundcategories.SoundCategories.parents;

public class SoundCategoriesPreLaunch implements PreLaunchEntrypoint {
    public static final Logger LOGGER = Logger.getLogger(SoundCategories.MODID);

    public static Map<CategoryLoader, List<Field>> getCategories() {
        return FabricLoader.getInstance().getEntrypoints(SoundCategories.MODID, CategoryLoader.class).stream()
                .collect(Collectors.toMap(it -> it, SoundCategoriesPreLaunch::getRegistrations));
    }

    private static List<Field> getRegistrations(CategoryLoader loader) {
        return Arrays.stream(loader.getClass().getDeclaredFields())
                .filter(it -> it.isAnnotationPresent(CategoryLoader.Register.class)).toList();
    }

    @Override
    public void onPreLaunch() {
        var init = SoundCategory.MASTER; //required so that the new categories are actually created, not actually used

        var cats = getCategories();
        for (CategoryLoader loader : cats.keySet()) {
            SoundCategory master = null;
            for (Field f : cats.get(loader)) {
                var annotation = f.getAnnotation(CategoryLoader.Register.class);
                try {
                    var category = (SoundCategory) f.get(loader);

                    if (annotation.master())
                        master = category;
                    else if (master != null)
                        parents.put(category, master);

                    if (annotation.defaultLevel() != 1.0)
                        defaultLevels.put(category, annotation.defaultLevel());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
