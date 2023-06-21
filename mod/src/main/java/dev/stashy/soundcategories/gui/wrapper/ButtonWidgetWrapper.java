package dev.stashy.soundcategories.gui.wrapper;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.lang.reflect.InvocationTargetException;

import static dev.stashy.soundcategories.gui.wrapper.VersionUtil.runIf;

public class ButtonWidgetWrapper {
    public static ButtonWidgetCreator creator;
    static MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

    static {
        runIf(">=1.19.3", () -> {
            creator = ((message, size, pos, onPress) -> {
                return null;
            });
        });

        runIf("<1.19.3", () -> {
            creator = ((message, size, pos, onPress) ->
            {
                try {
                    var buttonConstructor = ButtonWidget.class.getConstructor(int.class, int.class, int.class, int.class, Text.class, ButtonWidget.PressAction.class);
                    return buttonConstructor.newInstance(pos.getLeft(), pos.getRight(), size.getLeft(), size.getRight(), message, onPress);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    public interface ButtonWidgetCreator {
        ButtonWidget apply(Text message, Pair<Integer, Integer> size, Pair<Integer, Integer> position, ButtonWidget.PressAction onPress) throws InvocationTargetException, InstantiationException, IllegalAccessException;
    }
}
