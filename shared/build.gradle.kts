@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
}

kotlin {
    js()
    jvm()

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
                api(Deps.coroutinesRx2)
            }
        }

        val jvmTest by getting {
            dependencies {
                TestDeps.core.forEach(::api)
            }
        }
    }
}