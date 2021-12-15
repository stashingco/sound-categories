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
modImplementation "dev.stashy:soundcategories:${project.extrasounds_version}"
```

## Adding a category

In your `onInitializeClient` function you have to call the `SoundCategories.register` function. To keep a reference of
your category once it gets created, there is a callback which returns your new category.

```java
static SoundCategory CUSTOM_CATEGORY;

@Override
public void onInitializeClient(){
        register("CUSTOM_CATEGORY",(cat)->{CUSTOM_CATEGORY=cat;});
        }
```

## Localization

The newly added category needs language definitions to show any name, apart from its key. You can do so in your `lang`
folder. For an example, you can check the
[ExtraSounds](https://github.com/stashymane/extra-sounds/) language files.
