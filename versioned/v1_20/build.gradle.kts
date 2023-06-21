plugins {
    id("fabric-loom")
}

val archives_base_name: String by project.properties

val minecraft_version: String = "1.20"
val yarn_mappings: String = "1.20+build.1"
val loader_version: String = "0.14.21"

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:${yarn_mappings}:v2")

    modCompileOnly("net.fabricmc:fabric-loader:${loader_version}")

    implementation(project(":shared", "namedElements"))
}

loom {
    accessWidenerPath.set(file("src/main/resources/soundcategories-1_20.accesswidener"))
}

base {
    archivesName.set("$archives_base_name.1.20")
}

