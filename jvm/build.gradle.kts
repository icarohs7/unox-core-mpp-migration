plugins {
    kotlin("multiplatform")
    defaults.`platform-module`
}

kotlin {
    jvm()
    sourceSets {
        getByName("jvmMain") {
            dependencies {
                api(kotlin("stdlib-jdk7"))
            }
        }
    }
}