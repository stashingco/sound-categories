package dev.stashy.soundcategories;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class CustomSoundOptionsScreen extends GameOptionsScreen
{
    private SoundList list;

    public CustomSoundOptionsScreen(Screen parent, GameOptions options)
    {
        super(parent, options, new TranslatableText("options.sounds.title"));
    }

    protected void init()
    {
        this.list = new SoundList(this.client, this.width, this.height, 32, this.height - 32, 25);
        for (SoundCategory c : SoundCategory.values())
            this.list.addCategory(c);

        this.addSelectableChild(list);
        this.addDrawableChild(
                new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> {
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
