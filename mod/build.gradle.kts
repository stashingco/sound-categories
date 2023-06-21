import com.matthewprenger.cursegradle.CurseArtifact
import com.matthewprenger.cursegradle.CurseProject
import com.matthewprenger.cursegradle.CurseRelation
import com.matthewprenger.cursegradle.Options
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("fabric-loom")
    id("maven-publish")
    id("com.matthewprenger.cursegradle") version "1.4.0"
    id("com.modrinth.minotaur") version "2.2.0"
    id("org.jetbrains.changelog") version "2.1.0"
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

    include(modImplementation("dev.stashy:MixinSwap:1.0.0-SNAPSHOT") as Any)

    modImplementation(project(":shared", "namedElements"))
    modImplementation(project(":versioned:1.19.3", "namedElements"))
}

loom {
    accessWidenerPath.set(file("src/main/resources/soundcategories.accesswidener"))

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
    path.set(file("../CHANGELOG.md").canonicalPath)
    version.set(project.version.toString().split('-')[0])
}

val currentChangelog = project.changelog.getLatest()

curseforge {
    apiKey = System.getenv("CURSE_API_KEY") ?: ""
    project(closureOf<CurseProject> {
        id = "557374"
        changelog = currentChangelog.toHTML()
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

    changelog.set(currentChangelog.toText())
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
