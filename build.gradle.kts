buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")

    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // Hilt Dagger Plugins
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-P", "plugin:org.jetbrains.kotlin.kapt.experimental.parallel=true")
    }
}
tasks.withType<org.gradle.api.tasks.testing.Test> {
    enabled = false
}

allprojects {
    repositories {
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}