import org.gradle.api.JavaVersion
import com.android.build.gradle.LibraryExtension as AndroidBlock
import org.gradle.api.artifacts.dsl.RepositoryHandler as RepositoriesBlock
import org.gradle.kotlin.dsl.ScriptHandlerScope as BuildscriptBlock

fun AndroidBlock.defaultAndroidLibrarySettings() {
    compileSdkVersion(Versions.androidSdk)

    defaultConfig {
        minSdkVersion(Versions.androidMinSdk)
        targetSdkVersion(Versions.androidSdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments = mapOf("clearPackageData" to "true")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests.apply {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    packagingOptions {
        pickFirst("META-INF/library_release.kotlin_module")
        pickFirst("META-INF/lib_release.kotlin_module")
    }
}