@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
//    id("com.github.dcendents.android-maven") version "2.1"
    id("jacoco")
    defaults.`android-module`
}

android {
    defaultSettings()
    dataBinding {
        isEnabled = false
    }
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }
}

kotlin {
    jvm()
    js()
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api(CommonDeps.coroutinesCoreCommon)
            }
        }

        val commonTest by getting {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                api(kotlin("stdlib-jdk7"))
                api(Deps.arrowCore)
                api(Deps.arrowEffects)
                api(Deps.coroutinesRx2)
                api(Deps.rxKotlin)
                api(Deps.rxRelay)
            }
        }

        val jvmTest by getting {
            dependencies {
                api(kotlin("test-junit"))
                TestDeps.core.forEach(::api)
            }
        }

        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
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

        val androidTest by getting {
            dependsOn(jvmTest)
            dependencies {
                TestDeps.androidCore.forEach(::api)
            }
        }

        val jsMain by getting {
            dependencies {
                api(kotlin("stdlib-js"))
                api(JSDeps.coroutinesJs)
            }
        }

        val jsTest by getting {
            dependencies {
                api(kotlin("test-js"))
            }
        }
    }
}