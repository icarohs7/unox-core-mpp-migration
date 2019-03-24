plugins {
    kotlin("multiplatform")
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    defaults.`platform-module`
}

kotlin {
    android()
    sourceSets {
        getByName("androidMain") {
            dependencies {
                api(project(":jvm"))
            }
        }
    }
}