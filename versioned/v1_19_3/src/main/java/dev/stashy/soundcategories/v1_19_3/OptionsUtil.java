package dev.stashy.soundcategories.v1_19_3;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;

public class OptionsUtil {
    public static ButtonWidget getDoneButton(MinecraftClient client, Screen parent, int w, int h) {
        return ButtonWidget.builder(ScreenTexts.DONE, widget -> {
                    if (client == null) return;
                    client.options.write();
                    client.setScreen(parent);
                })
                .position((w - 310) / 2, h - 27)
                .size(310, 20)
                .build();
    }
}
