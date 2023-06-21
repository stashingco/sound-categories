plugins {
    id("fabric-loom")
}

val mod_version: String by project.properties
val maven_group: String by project.properties
val archives_base_name: String by project.properties

version = mod_version
group = maven_group

val minecraft_version: String = "1.19.3"
val yarn_mappings: String = "1.19.3+build.5"

dependencies {
    implementation(project(":shared"))
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:${yarn_mappings}:v2")
}

base {
    archivesName.set("$archives_base_name.1.19.3")
}

tasks {
    withType<ProcessResources>() {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}
