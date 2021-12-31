// import com.android.build.gradle.internal.api.DefaultAndroidSourceDirectorySet
// import java.util.Properties

plugins {
    `maven-publish`
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        // Classpath dependencies is not fully supported yet ref: https://github.com/gradle/gradle/issues/17792
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

ext {
    val artifactId = "android-tvprovider"
    val groupId = "com.afzaln"
    val version = "1.1.0-alpha01"
    val description = "AndroidX TVProvider library with programs query support"
}

subprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

