package dev.stashy.soundcategories.gui.wrapper;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.lang.reflect.InvocationTargetException;

public class ButtonWidgetWrapper extends WidgetWrapper {
    public static ButtonWidgetCreator creator;

    static {
        runIfVersion(">=1.19.3", () -> {
            creator = ((message, size, pos, onPress) -> {
                try {
                    var builderMethod = ButtonWidget.class.getMethod("builder", Text.class, ButtonWidget.PressAction.class);
                    Object builder = builderMethod.invoke(null, message, onPress);

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
        });

        runIfVersion("<1.19.3", () -> {
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
