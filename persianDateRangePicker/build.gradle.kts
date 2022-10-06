plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    `maven-publish`
}

android {
    namespace = "com.alirezamilani.persiandaterangepicker"
    compileSdk = ConfigData.compileSdk

    defaultConfig {
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk

        aarMetadata {
            minCompileSdk = ConfigData.minSdk
        }
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(Dependencies.ANDROIDX_COMPOSE_MATERIAL)
    implementation(Dependencies.ANDROIDX_COMPOSE_MATERIAL3)
    implementation(Dependencies.ANDROIDX_COMPOSE_UI_PREVIEW)

    debugImplementation(Dependencies.ANDROIDX_COMPOSE_UI_TOOLING)

    testImplementation(Dependencies.JUNIT)
}

tasks {
    dokkaHtml.configure {
        dokkaSourceSets {
            named("main") {
                noAndroidSdkLink.set(false)
            }
        }
    }

    register<Jar>("dokkaJar") {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        archiveClassifier.set("javadoc")
        from(dokkaHtml)
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.alirezamilani"
            artifactId = "core"
            version = ConfigData.versionName

            afterEvaluate {
                from(components["release"])

                artifact(tasks["dokkaJar"])
            }

            pom {
                name.set("NinjaAppDev Core")
                description.set("This library consist of core classes for NinjaAppDev`s project")
                url.set("https://www.ninjaappdev.com/directory/")
                inceptionYear.set("2018")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("alireza_milani")
                        name.set("Alireza Milani")
                        email.set("alireza.milani2011@gmail.com")
                    }
                }
            }
        }
    }
}