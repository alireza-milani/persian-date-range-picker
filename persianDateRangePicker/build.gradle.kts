//import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")/* version "0.24.0"*/
    `maven-publish`
    signing
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

    /*mavenPublishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }*/
}

dependencies {
    implementation(platform(Dependencies.ANDROIDX_COMPOSE_BOM))
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
            groupId = "io.github.alireza-milani"
            artifactId = "persian-date-range-picker"
            version = ConfigData.versionName

            afterEvaluate {
                from(components["release"])

                artifact(tasks["dokkaJar"])
            }

            pom {
                name.set("Persian Date Range Picker")
                description.set("This library consist of classes for create data range picker in compose project")
                inceptionYear.set("2022")

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

                scm {
                    connection.set("cm:git:git://github.com/Alireza-Milani/persian-date-range-picker.git")
                    developerConnection.set("scm:git:ssh://github.com/Alireza-Milani/persian-date-range-picker.git")
                    url.set("https://github.com/Alireza-Milani/persian-date-range-picker/tree/master")
                }
            }
        }
    }
}

//group = "io.github.alireza-milani"
//version = ConfigData.versionNam

/*
mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)

    signAllPublications()

//    afterEvaluate {
//        from(components["release"])
//
//        artifact(tasks["dokkaJar"])
//    }

    coordinates("io.github.alireza-milani", "persian-date-range-picker", ConfigData.versionName)

    pom {
        name.set("Persian Date Range Picker")
        description.set("This library consist of classes for create data range picker in compose project")
        inceptionYear.set("2023")
        url.set("https://github.com/Alireza-Milani/persian-date-range-picker/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("alireza_milani")
                name.set("Alireza Milani")
                email.set("alireza.milani2011@gmail.com")
                url.set("https://github.com/Alireza-Milani/")
            }
        }
        scm {
            url.set("https://github.com/Alireza-Milani/persian-date-range-picker/")
            connection.set("scm:git:git://github.com/Alireza-Milani/persian-date-range-picker.git")
            developerConnection.set("scm:git:ssh://git@github.com:Alireza-Milani/persian-date-range-picker.git")
        }
    }
}*/
