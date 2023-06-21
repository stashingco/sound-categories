plugins {
    id("fabric-loom")
}

val archives_base_name: String by project.properties
val minecraft_version: String by project.properties
val yarn_mappings: String by project.properties
val loader_version: String by project.properties

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:${yarn_mappings}:v2")

    modCompileOnly("net.fabricmc:fabric-loader:${loader_version}")
}

base {
    archivesName.set("$archives_base_name.shared")
}
