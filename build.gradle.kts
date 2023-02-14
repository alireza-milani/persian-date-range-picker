plugins {
    id("com.android.application") version Versions.ANDROID_GRADLE_PLUGIN apply false
    id("com.android.library") version Versions.ANDROID_GRADLE_PLUGIN apply false
    kotlin("android") version Versions.KOTLIN apply false
    id("org.jetbrains.dokka") version Versions.DOKKA apply false
    id("com.vanniktech.maven.publish") version "0.24.0" apply false
}