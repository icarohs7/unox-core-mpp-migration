plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("com.android.tools.build:gradle:3.3.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.21")
}

repositories {
    google()
    jcenter()
}