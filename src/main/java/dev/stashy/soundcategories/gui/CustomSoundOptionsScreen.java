package dev.stashy.soundcategories.gui;

import dev.stashy.soundcategories.SoundCategories;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class CustomSoundOptionsScreen extends GameOptionsScreen
{
    private SoundList list;

    public CustomSoundOptionsScreen(Screen parent, GameOptions options)
    {
        super(parent, options, Text.translatable("options.sounds.title"));
    }

    protected void init()
    {
        this.list = new SoundList(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addCategory(SoundCategory.MASTER);
        SoundCategory[] cats = Arrays.stream(SoundCategory.values())
                         .filter(it -> !SoundCategories.parents.containsKey(
                                 it) && !SoundCategories.parents.containsValue(it))
                         .skip(1).toArray(SoundCategory[]::new);
        var count = cats.length;
        for (int i = 0; i < Math.ceil(count); i += 2)
            list.addDoubleCategory(cats[i], i + 1 < count ? cats[i + 1] : null);
        this.list.addSingleOptionEntry(gameOptions.getSoundDevice());
        this.list.addAll(new SimpleOption[]{ gameOptions.getShowSubtitles(), gameOptions.getDirectionalAudio() });

        Arrays.stream(SoundCategory.values()).filter(SoundCategories.parents::containsValue).forEach(it -> {
            this.list.addGroup(it, button -> {this.client.setScreen(new SoundGroupOptionsScreen(this, gameOptions, it));});
        });

        this.addSelectableChild(this.list);

        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
                    this.client.options.write();
                    this.client.setScreen(this.parent);
                })
                        .dimensions(this.width / 2 - 100, this.height - 27, 200, 20)
                        .build()
        );
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
