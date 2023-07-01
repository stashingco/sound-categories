import com.matthewprenger.cursegradle.CurseArtifact
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import com.matthewprenger.cursegradle.Options
import net.fabricmc.loom.task.RemapJarTask
import org.jetbrains.changelog.Changelog

plugins {
    id("fabric-loom") version "1.2-SNAPSHOT"
    id("maven-publish")
    id("com.matthewprenger.cursegradle") version "1.4.0"
    id("com.modrinth.minotaur") version "2.2.0"
    id("org.jetbrains.changelog") version "2.1.0"
}

allprojects {
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
            val props = arrayOf("modHomePage", "modSources", "modLicense").map { it to project.properties[it] }
            inputs.property("version", project.version)
            props.forEach { inputs.property(it.first, it.second) }

            filesMatching("fabric.mod.json") {
                expand(mapOf("version" to project.version).plus(props))
            }
        }
    }
}

subprojects {
    dependencies {
        pluginManager.withPlugin("fabric-loom") {
            modCompileOnly("dev.stashy:MixinSwap:1.0.0-SNAPSHOT")
        }
    }
}

val compatibleVersions = listOf("1.19.3", "1.19.2", "1.19.1", "1.19")

val archives_base_name: String by project.properties
val minecraft_version: String by project.properties
val yarn_mappings: String by project.properties
val loader_version: String by project.properties
val api_version: String by project.properties

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings("net.fabricmc:yarn:${yarn_mappings}:v2")

    modImplementation("net.fabricmc:fabric-loader:${loader_version}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${api_version}")

    modImplementation("dev.stashy:MixinSwap:1.0.0-SNAPSHOT")?.let { include(it) }

    api(project(":shared", "namedElements"))?.let { include(it) }
    implementation(project(":versioned:v1_19", "namedElements"))?.let { include(it) }
    implementation(project(":versioned:v1_19_3", "namedElements"))?.let { include(it) }
    implementation(project(":versioned:v1_20", "namedElements"))?.let { include(it) }
}


loom {
    runs {
        named("client") {
            source(sourceSets.getByName("test"))
        }
    }
}

base {
    archivesName.set(archives_base_name)
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}" }
        }
    }
}

val publish_username: String? by project
val publish_password: String? by project

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        maven("https://repo.stashy.dev") {
            credentials {
                username = publish_username ?: ""
                password = publish_password ?: ""
            }
        }
    }
}

changelog {
    version.set(project.version.toString().split('-')[0])
}

val currentChangelog = project.changelog.getLatest()

curseforge {
    apiKey = System.getenv("CURSE_API_KEY") ?: ""
    project(closureOf<CurseProject> {
        id = "557374"
        changelog = project.changelog.renderItem(currentChangelog, Changelog.OutputType.HTML)
        changelogType = "html"
        releaseType = "release"
        compatibleVersions.forEach { ver ->
            addGameVersion(ver)
        }

        mainArtifact(tasks.withType<RemapJarTask>(), closureOf<CurseArtifact> {
            displayName = version
            relations(closureOf<CurseRelation> {
                requiredDependency("fabric-api")
            })
        })
    })

    options(closureOf<Options> {
        forgeGradleIntegration = false
        detectNewerJava = true
    })
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN") ?: "")
    projectId.set("GROGt4v1")

    changelog.set(project.changelog.renderItem(currentChangelog, Changelog.OutputType.MARKDOWN))
    versionNumber.set(project.version.toString())
    versionName.set(project.version.toString())
    uploadFile.set(tasks.withType<RemapJarTask>())
    gameVersions.set(compatibleVersions)
    loaders.set(listOf("fabric"))
}

tasks.register("publishMod") {
    dependsOn(tasks.modrinth)
    dependsOn(tasks.curseforge)
}
