plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.alirezamilani.app"
    compileSdk = ConfigData.compileSdk

    defaultConfig {
        applicationId = "com.alirezamilani.app"
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":persianDateRangePicker"))
    implementation(platform(Dependencies.ANDROIDX_COMPOSE_BOM))
    implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(Dependencies.ANDROIDX_COMPOSE_MATERIAL3)
    implementation(Dependencies.ANDROIDX_COMPOSE_UI)
    implementation(Dependencies.ANDROIDX_COMPOSE_UI_PREVIEW)
    debugImplementation(Dependencies.ANDROIDX_COMPOSE_UI_TOOLING)
}