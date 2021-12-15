# Sound Categories

Fabric library that allows mods to add more sound categories, and modifies the Minecraft sound settings menu to fit as
many categories as required.

## Adding to your project

The build artifact is hosted on [my personal Maven server](https://repo.stashy.dev). Add the following to your
repositories block:

```groovy
maven {
    name = "stashymane's repo"
    url = "https://repo.stashy.dev"
}
```

And the following to your dependencies:

```groovy
modImplementation include("dev.stashy:sound-categories:${project.soundcategories_version}")
```

Make sure to keep the `include()` part to embed the library into your mod, as otherwise the user will have to download
it separately.

## Adding a category

Classes that contain sound categories must implement `CategoryLoader` so that they are picked up by the loader. Every
SoundCategory you define must have the `CategoryLoader.Register` annotation - they will have the category reference
injected once it is created.

Example class:

```java
import dev.stashy.soundcategories.CategoryLoader;
import net.minecraft.sound.SoundCategory;

public class CustomCategories implements CategoryLoader
{
    @CategoryLoader.Register
    public static SoundCategory CUSTOM1;
    @CategoryLoader.Register(name = "custom_category_name")
    public static SoundCategory CUSTOM2;
}
```

After implementing the loader, make sure you add the class as an entrypoint for `sound-categories` in
your `fabric.mod.json`.

## Localization

The newly added category needs language definitions to show any name, apart from its key. You can do so in your `lang`
folder. For an example, you can check the
[ExtraSounds](https://github.com/stashymane/extra-sounds/) language files.
