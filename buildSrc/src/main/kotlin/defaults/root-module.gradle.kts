package defaults

repositories {
    maven("https://kotlin.bintray.com/kotlinx")
    google()
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    jcenter()
}

tasks {
    "clean" {
        delete(buildDir)
    }
}