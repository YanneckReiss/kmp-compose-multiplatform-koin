import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")

    //<editor-fold desc="// ...">
    // Enabled KSP for the shared module
    id("com.google.devtools.ksp")
    //</editor-fold>
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {

        val commonMain by getting {

            //<editor-fold desc="// ...">
            // Required so we can access the generated KSP files
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            //</editor-fold>

            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                ///// KOIN /////
                implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.3"))
                implementation("io.insert-koin:koin-core")
                implementation("io.insert-koin:koin-compose")

                //<editor-fold desc="// ...">
                ///// KOIN ANNOTATIONS /////
                // Only required if you want to use koin annotations
                implementation("io.insert-koin:koin-annotations:1.3.0")
                //</editor-fold>
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

//<editor-fold desc="// ...">
// Add Koin KSP compiler dependency for code generation in KSP.
// Source: https://github.com/InsertKoinIO/hello-kmp/blob/annotations/shared/build.gradle.kts
dependencies {
    add("kspCommonMainMetadata", "io.insert-koin:koin-ksp-compiler:1.3.0")
}

// Ensure all Kotlin compile tasks depend on kspCommonMainKotlinMetadata for prior code processing.
tasks.withType<KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

// Make all SourcesJar tasks depend on kspCommonMainKotlinMetadata.
afterEvaluate {
    tasks.filter { task: Task ->
        task.name.contains("SourcesJar", true)
    }.forEach {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }
}
//</editor-fold>

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
