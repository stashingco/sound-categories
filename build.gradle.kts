plugins {
    id("fabric-loom") version "1.2-SNAPSHOT" apply false
}

subprojects {
    val mod_version: String by project.properties
    val maven_group: String by project.properties

    version = mod_version
    group = maven_group

    repositories {
        maven("https://repo.stashy.dev/snapshots")
        maven("https://jitpack.io")
    }

    tasks {
        withType<ProcessResources>() {
            inputs.property("version", project.version)

            filesMatching("fabric.mod.json") {
                expand("version" to project.version)
            }
        }
    }

}
