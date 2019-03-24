@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    compileSdkVersion(Versions.androidSdk)
    defaultConfig {
        minSdkVersion(Versions.androidMinSdk)
        targetSdkVersion(Versions.androidSdk)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    js()
    jvm()
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
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jsMain by getting {
            dependencies {
                api(kotlin("stdlib-js"))
            }
        }

        val jvmMain by getting {
            dependencies {
                api(kotlin("stdlib-jdk7"))
            }
        }

        val jvmTest by getting {
            dependencies {
                TestDeps.core.forEach(::api)
            }
        }

        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
                api(AndroidDeps.coroutinesAndroid)
            }
        }

        val androidTest by getting {
            dependsOn(jvmTest)
        }
    }
}