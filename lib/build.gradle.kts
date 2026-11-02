import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.publish)
}

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
            api(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.animation)
            api(libs.compose.multiplatform.ui)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.pratyushsingh07", "bumblecardswipe", "3.0.0")

    pom {
        name.set("BumbleCardSwipe")
        description.set("Bumble-like swipeable card gesture for Compose Multiplatform")
        inceptionYear.set("2023")
        url.set("https://github.com/PratyushSingh07/BumbleCardSwipe")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("PratyushSingh07")
                name.set("Pratyush Singh")
                url.set("https://github.com/PratyushSingh07")
            }
            developer {
                id.set("aditya-gupta99")
                name.set("Aditya Gupta")
                url.set("https://github.com/aditya-gupta99")
            }
        }
        scm {
            url.set("https://github.com/PratyushSingh07/BumbleCardSwipe")
            connection.set("scm:git:git://github.com/PratyushSingh07/BumbleCardSwipe.git")
            developerConnection.set("scm:git:ssh://git@github.com/PratyushSingh07/BumbleCardSwipe.git")
        }
    }
}
