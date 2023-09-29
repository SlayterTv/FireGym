buildscript {
    dependencies {
        //esta version de hilt solo funciona con la version 1.6.21 eso seria id 'org.jetbrains.kotlin.android' version '1.6.21' apply false
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
    repositories {
        mavenCentral()
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.3.15" apply false
}