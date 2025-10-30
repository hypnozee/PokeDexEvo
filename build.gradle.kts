plugins {
    id("com.android.application") version "8.13.0" apply false
    id("com.android.library") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.21" apply false
    alias(libs.plugins.kotlin.jvm) apply false
}

// Centralize Java/Kotlin target configuration for all modules
subprojects {
    // Set Java compile compatibility for plain JavaCompile tasks (applies to JVM modules)
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    // Set Kotlin JVM target for any Kotlin compile task
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    // Configure Android modules (both app and library) to use Java 21
    plugins.withId("com.android.application") {
        // AGP 8: configure via ApplicationExtension
        extensions.configure(com.android.build.api.dsl.ApplicationExtension::class.java) {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }
    }
    plugins.withId("com.android.library") {
        // AGP 8: configure via LibraryExtension
        extensions.configure(com.android.build.api.dsl.LibraryExtension::class.java) {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }
    }

    // Configure Kotlin Android plugin's default JVM target via project extension when present
    plugins.withId("org.jetbrains.kotlin.android") {
        extensions.configure(org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension::class.java) {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            }
        }
    }

    // Configure pure Kotlin/JVM modules' toolchain (e.g., :domain)
    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure(org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension::class.java) {
            jvmToolchain(21)
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}