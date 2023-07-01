package dev.stashy.soundcategories.v1_19;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;

public class OptionsUtil {
    public static ButtonWidget getDoneButton(MinecraftClient client, Screen parent, int w, int h) {
        return new ButtonWidget((w - 310) / 2, h - 27, 310, 20, ScreenTexts.DONE, widget -> {
            if (client == null) return;
            client.options.write();
            client.setScreen(parent);
        });
    }
}
