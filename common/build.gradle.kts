plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
    }
}