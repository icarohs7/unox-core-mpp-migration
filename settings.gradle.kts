rootProject.name = "unox-core"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin.")) {
                useVersion(Versions.kotlin)
            }
        }
    }
}