@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.github.dcendents.android-maven") version "2.1"
    id("jacoco")
    defaults.`android-module`
}

android {
    sourceSets["main"].java.srcDir("src/androidMain/kotlin")
    sourceSets["main"].java.srcDir("src/main/kotlin")

    sourceSets["test"].java.srcDir("src/androidTest/kotlin")
    sourceSets["test"].java.srcDir("src/test/kotlin")

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
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                api(Deps.kotlinStdLib)
                api(Deps.arrowCore)
                api(Deps.arrowEffects)
                api(Deps.coroutinesRx2)
                api(Deps.rxKotlin)
                api(Deps.rxRelay)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                TestDeps.core.forEach(::implementation)
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
            dependsOn(androidMain)
            dependencies {
                implementation(kotlin("test-junit"))
                TestDeps.androidCore.forEach {
                    implementation(it) {
                        exclude(group = "org.apache.maven")
                    }
                }
            }
        }

        val pattern = Regex("(.*)android(.*)[tT]est(.*)")
        forEach { sourceSet ->
            when {
                sourceSet.name == "androidTest" -> return@forEach
                sourceSet.name.matches(pattern) -> sourceSet.dependsOn(androidTest)
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
                implementation(kotlin("test-js"))
            }
        }
    }
}

jacoco {
    toolVersion = "0.8.3"
}

tasks {
    withType<Test> {
        extensions.getByType<JacocoTaskExtension>().setIncludeNoLocationClasses(true)
    }

    create<JacocoReport>("jacocoTestReport") {
        dependsOn("testDebugUnitTest", "check", "createDebugCoverageReport")

        this.group = "Reporting"
        this.description = "Generate Jacoco coverage reports for Debug build"

        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }

        val excludes = listOf(
                "**/R.class",
                "**/R\$*.class",
                "**/*\$ViewInjector*.*",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*",
                "android/**/*.*"
        )

        classDirectories.setFrom(
                fileTree("$buildDir/intermediates/classes/debug") { exclude(excludes) },
                fileTree("$buildDir/tmp/kotlin-classes/debug") { exclude(excludes) }
        )
        sourceDirectories.setFrom(files(
                android.sourceSets["main"].java.srcDirs,
                "src/main/kotlin"
        ))
        
        executionData("$buildDir/jacoco/testDebugUnitTest.exec")
        executionData("$buildDir/jacoco/jvmTest.exec")
    }
}