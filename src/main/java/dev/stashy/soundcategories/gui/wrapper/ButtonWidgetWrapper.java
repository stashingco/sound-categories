package dev.stashy.soundcategories.gui.wrapper;

import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.fabricmc.loader.impl.util.version.VersionPredicateParser;
import net.minecraft.MinecraftVersion;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.lang.reflect.InvocationTargetException;

public class ButtonWidgetWrapper {
    public static ButtonWidgetCreator creator;

    static {
        Version version;
        VersionPredicate pred;
        try {
            version = Version.parse(MinecraftVersion.CURRENT.getName());
            pred = VersionPredicateParser.parse(">=1.19.3");
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
        if (pred.test(version)) {
            creator = ((message, size, pos, onPress) -> {
                Object builder = null;
                try {
                    var builderMethod = ButtonWidget.class.getMethod("builder", Text.class, ButtonWidget.PressAction.class);
                    builder = builderMethod.invoke(null, message, onPress);

                    var sizeMethod = builder.getClass().getMethod("size", int.class, int.class);
                    builder = sizeMethod.invoke(builder, size.getLeft(), size.getRight());

                    var positionMethod = builder.getClass().getMethod("position", int.class, int.class);
                    builder = positionMethod.invoke(builder, pos.getLeft(), pos.getRight());

                    var buildMethod = builder.getClass().getMethod("build");
                    return (ButtonWidget) buildMethod.invoke(builder);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            creator = ((message, size, pos, onPress) ->
            {
                try {
                    var buttonConstructor = ButtonWidget.class.getConstructor(int.class, int.class, int.class, int.class, Text.class, ButtonWidget.PressAction.class);
                    return buttonConstructor.newInstance(pos.getLeft(), pos.getRight(), size.getLeft(), size.getRight(), message, onPress);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    public interface ButtonWidgetCreator {
        ButtonWidget apply(Text message, Pair<Integer, Integer> size, Pair<Integer, Integer> position, ButtonWidget.PressAction onPress) throws InvocationTargetException, InstantiationException, IllegalAccessException;
    }
}
