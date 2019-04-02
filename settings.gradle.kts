rootProject.name = "unox-core"

include(":shared")
include(":js")
include(":jvm")
//include(":android")

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            val conditions = listOf(
                    requested.id.id.startsWith("org.jetbrains.kotlin."),
                    requested.id.id == "kotlin2js"
            )

            if (conditions.any { it })
                useVersion(Versions.kotlin)

            if (requested.id.id == "kotlin2js")
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        }

        repositories {
            google()
            jcenter()
        }
    }
}

enableFeaturePreview("GRADLE_METADATA")