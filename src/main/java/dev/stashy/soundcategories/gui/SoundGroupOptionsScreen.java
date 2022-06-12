package dev.stashy.soundcategories.gui;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Arrays;

public class SoundGroupOptionsScreen extends GameOptionsScreen
{
    private final SoundCategory parentCategory;

    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category)
    {
        super(parent, gameOptions, Text.translatable("soundCategory." + category.getName()));
        parentCategory = category;
    }

    private SoundList list;

    protected void init()
    {
        this.list = new SoundList(this.client, this.width, this.height, 32, this.height - 32, 25);
        Arrays.stream(SoundCategory.values())
              .filter(it -> SoundCategories.parents.containsKey(it)
                      && SoundCategories.parents.get(it) == parentCategory)
              .forEach(it -> list.addCategory(it));

        this.addSelectableChild(list);

        this.addDrawableChild(
                new ButtonWidget(this.width / 2 - 155, this.height - 27, 310, 20, ScreenTexts.DONE, (button) -> {
                    this.client.options.write();
                    this.client.setScreen(this.parent);
                }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
