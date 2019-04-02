@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    js()

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