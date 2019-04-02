plugins {
    id("com.android.application")
    id("kotlin-android")
    defaults.`android-module`
}

android {
    sourceSets["main"].java.srcDir("src/main/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")
    defaultSettings()
}

dependencies {
    api(project(":shared"))

    api(AndroidDeps.appCompat)
    api(AndroidDeps.coreKtx)
    api(AndroidDeps.coroutinesAndroid)
    api(AndroidDeps.disposer)
    api(AndroidDeps.lifecycleExtensions)
    api(AndroidDeps.lives)
    api(AndroidDeps.rxAndroid)
    api(AndroidDeps.timber)
}
