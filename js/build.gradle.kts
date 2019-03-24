plugins {
    kotlin("multiplatform")
    defaults.`platform-module`
}

kotlin {
    js()
    sourceSets {
        getByName("jsMain") {
            dependencies {
                api(kotlin("stdlib-js"))
            }
        }
    }
}