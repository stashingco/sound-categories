package dev.stashy.soundcategories.v1_19_3;

import dev.stashy.soundcategories.SCGuiLayout;
import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SCSoundList extends ElementListWidget<SCSoundList.Entry> {
    private final GameOptions options;

    public SCSoundList(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        options = minecraftClient.options;
        this.centerListVertically = false;
    }

    public SCSoundList(MinecraftClient minecraftClient, int width, int height) {
        this(minecraftClient, width, height, 32, height - 32, 25);
    }

    public int addCategories(SoundCategory... cats) {
        return super.addEntry(Entry.createCategory(options, this.width, cats));
    }

    public int addOptions(SimpleOption<?>... opts) {
        return super.addEntry(Entry.create(options, this.width, opts));
    }

    public int addGroup(SoundCategory group, ButtonWidget.PressAction settingsPressAction) {
        return super.addEntry(Entry.createGroup(options, this.width, group, settingsPressAction));
    }

    @Override
    public int getRowWidth() {
        return 400;
    }
    
    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 32;
    }

    public static class Entry extends ElementListWidget.Entry<Entry> {
        private static final int totalWidth = 310;
        List<? extends ClickableWidget> widgets;

        public Entry(List<? extends ClickableWidget> w) {
            widgets = w;
        }

        public static Entry createCategory(GameOptions gameOptions, int screenWidth, SoundCategory... cats) {
            return create(gameOptions, screenWidth, Arrays.stream(cats).map(gameOptions::getSoundVolumeOption).toArray(SimpleOption[]::new));
        }

        public static Entry create(GameOptions gameOptions, int screenWidth, SimpleOption<?>... opts) {
            int margin = 10;

            List<ClickableWidget> entries = new ArrayList<>();

            for (int i = 0; i < opts.length; i++) {
                var layout = SCGuiLayout.flowLayout(i, opts.length, totalWidth, margin);
                var btn = opts[i].createButton(gameOptions, (screenWidth - totalWidth) / 2 + layout.x(), 0, layout.width());
                entries.add(btn);
            }

            return new Entry(entries);
        }

        public static Entry createGroup(GameOptions gameOptions, int screenWidth, SoundCategory group, ButtonWidget.PressAction settingsPressAction) {
            int buttonWidth = 20;
            int margin = 10;
            int offset = (screenWidth - totalWidth) / 2;
            return new Entry(
                    List.of(
                            gameOptions.getSoundVolumeOption(group).createButton(gameOptions, offset, 0, totalWidth - buttonWidth - margin),
                            new TexturedButtonWidget(offset + totalWidth - buttonWidth, 0, 20, 20, 0, 0, 20, SoundCategories.SETTINGS_ICON, 20, 40, settingsPressAction)
                    )
            );
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return this.widgets;
        }

        @Override
        public List<? extends Element> children() {
            return this.widgets;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.widgets.forEach((s) -> {
                s.setY(y);
                s.render(matrices, mouseX, mouseY, tickDelta);
            });
        }
    }
}
