package dev.stashy.soundcategories.v1_20;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class SCGroupScreen extends GameOptionsScreen {
    private final SoundCategory group;
    private SCSoundList list;

    public SCGroupScreen(Screen parent, GameOptions options, SoundCategory group) {
        super(parent, options, Text.translatable("soundCategory." + group.getName()));
        this.group = group;
    }

    public void init() {
        list = new SCSoundList(client, width, height);
        SoundCategories.getChildren(group).forEach(list::addCategories);
        addSelectableChild(list);
        addDrawableChild(OptionsUtil.getDoneButton(client, parent, width, height));
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 20, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }
}
