plugins {
    defaults.`root-module`
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("kapt")
}

android {
    defaultAndroidLibrarySettings()
}

kotlin {
    jvm()
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
            }
        }

        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
            }
        }
    }
}

dependencies {

}