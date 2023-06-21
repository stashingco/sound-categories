package dev.stashy.soundcategories.gui.wrapper;

import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.fabricmc.loader.impl.util.version.VersionPredicateParser;
import net.minecraft.MinecraftVersion;

public class VersionUtil {
    static Version gameVersion;

    static {
        try {
            gameVersion = Version.parse(MinecraftVersion.CURRENT.getName());
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runIf(String version, Runnable fn) {
        try {
            VersionPredicate pred = VersionPredicateParser.parse(">=1.19.3");
            if (pred.test(gameVersion)) fn.run();
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
    }
}
