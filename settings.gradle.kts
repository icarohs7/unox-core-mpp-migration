rootProject.name = "unox-core"

listOf(
        ":common",
        ":js",
        ":jvm"
).forEach(::include)

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin.")) {
                useVersion(Versions.kotlin)
            }
        }
    }
}