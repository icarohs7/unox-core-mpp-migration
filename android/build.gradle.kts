buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.2")
    }
}

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    defaultAndroidLibrarySettings()
    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    api(project(":shared"))
}
