package dev.stashy.soundcategories;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.SoundSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundList extends ElementListWidget<SoundList.SoundEntry>
{

    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l, int m)
    {
        super(minecraftClient, i, j, k, l, m);
        this.centerListVertically = false;
    }

    public int addCategory(SoundCategory cat)
    {
        return super.addEntry(SoundEntry.create(cat, this.width));
    }

    public int addDoubleCategory(SoundCategory first, SoundCategory second)
    {
        return super.addEntry(SoundEntry.createDouble(first, second, this.width));
    }

    public int addOption(GameOptions o, Option w)
    {
        return super.addEntry(SoundEntry.createOption(o, w, this.width));
    }

    public int addGroup(SoundCategory group)
    {
        return super.addEntry(SoundEntry.createGroup(group, this.width));
    }

    public int getRowWidth()
    {
        return 400;
    }

    protected int getScrollbarPositionX()
    {
        return super.getScrollbarPositionX() + 32;
    }

    @Environment(EnvType.CLIENT)
    protected static class SoundEntry extends ElementListWidget.Entry<SoundList.SoundEntry>
    {
        List<SoundSliderWidget> sliders;

        public SoundEntry(List<? extends ClickableWidget> w)
        {
            sliders = w;
        }

        public static SoundEntry create(SoundCategory cat, int width)
        {
            return new SoundEntry(Arrays.asList(
                    new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 - 155, 0, cat, 310)));
        }

        public static SoundEntry createDouble(SoundCategory first, @Nullable SoundCategory second, int width)
        {
            List<SoundSliderWidget> w = new ArrayList<>();
            w.add(new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 - 155, 0, first, 150));
            if (second != null)
                w.add(new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 + 5, 0, second, 150));
            return new SoundEntry(w);
        }

        public static SoundEntry createOption(GameOptions o, Option w, int width)
        {
            var b = w.createButton(o, width / 2 - 155, 0, 310);
            return new SoundEntry(List.of(b));
        }

        public static SoundEntry createGroup(SoundCategory group, int width)
        {
            return new SoundEntry(
                    List.of(
                            new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 - 155, 0, group, 285),
//                            new ButtonWidget(width / 2 + 135, 0, 20, 20, Text.of("X"), button -> {}),
                            new TexturedButtonWidget(width / 2 + 135, 0, 20, 20, 0, 0,
                                                     new Identifier("soundcategories", "textures/gui/cog.png"),
                                                     button -> {

                                                     })
                    ));
        }

        public List<? extends Element> children()
        {
            return this.sliders;
        }

        public List<? extends Selectable> selectableChildren()
        {
            return this.sliders;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            this.sliders.forEach((s) -> {
                s.y = y;
                s.render(matrices, mouseX, mouseY, tickDelta);
            });
        }
    }
}
