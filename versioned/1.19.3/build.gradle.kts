plugins {
    id("fabric-loom")
}

val archives_base_name: String by project.properties

val minecraft_version: String = "1.19.3"
val yarn_mappings: String = "1.19.3+build.5"

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:${yarn_mappings}:v2")

    modCompileOnly(project(":shared"))
}

base {
    archivesName.set("$archives_base_name.1.19.3")
}
