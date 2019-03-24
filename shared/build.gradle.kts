import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    js()

    sourceSets {
        get("commonMain") {
            dependencies {
                api(kotlin("stdlib-common"))
            }
        }

        get("jsMain") {
            dependencies {
                api(kotlin("stdlib-js"))
            }
        }

        get("jvmMain") {
            dependencies {
                api(kotlin("stdlib-jdk7"))
            }
        }
    }
}

fun NamedDomainObjectContainer<KotlinSourceSet>.get(
        sourceSetName: String,
        action: Action<KotlinSourceSet>
): KotlinSourceSet {
    return getByName(sourceSetName, action)
}