plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.myapplication.MyApplication"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

    dependencies {
        implementation(platform("io.insert-koin:koin-bom:3.5.3"))
        implementation("io.insert-koin:koin-core")
        implementation("io.insert-koin:koin-android")

        //<editor-fold desc="Koin Annotations">
        // Annotations
        implementation("io.insert-koin:koin-annotations:1.3.0")
        ksp("io.insert-koin:koin-ksp-compiler:1.3.0")
        //</editor-fold>
    }
}
