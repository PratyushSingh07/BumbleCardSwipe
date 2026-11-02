import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

group = "com.github.PratyushSingh07"
version = "2.0.0"

kotlin {
    android {
        namespace = "com.pratyush.swipeablecard"
        compileSdk = 37
        minSdk = 24
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.animation)
            implementation(libs.compose.multiplatform.ui)
        }
    }
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        artifactId = artifactId.replace(project.name, "BumbleCardSwipe")
    }
}
