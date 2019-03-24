plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("kapt")
    defaults.`root-module`
}

android {
    defaultAndroidLibrarySettings()
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        getByName("commonTest") {}

        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("js"))
            }
        }

        js().compilations["test"].defaultSourceSet {}

        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
                api(Deps.arrowCore)
                api(Deps.coroutinesRx2)
                api(Deps.rxJava2)
                api(Deps.rxKotlin)
                api(Deps.rxRelay)
            }
        }

        jvm().compilations["test"].defaultSourceSet {}

        android()
        getByName("androidMain") {
            dependsOn(getByName("jvmMain"))
            dependencies {
                api(AndroidDeps.ankoCommons)
                api(AndroidDeps.appCompat)
                api(AndroidDeps.coreKtx)
                api(AndroidDeps.coroutinesAndroid)
                api(AndroidDeps.disposer)
                api(AndroidDeps.lifecycleExtensions)
                api(AndroidDeps.lives)
                api(AndroidDeps.rxAndroid)
                api(AndroidDeps.timber)
            }
        }

        getByName("androidTest") {}
    }
}