rootProject.name = "StorageManager2"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            library("lombok", "org.projectlombok:lombok:1.18.46")
            library("slf4j", "org.slf4j:slf4j-api:2.0.17")
            library("logback", "ch.qos.logback:logback-classic:1.5.32")
            library("jetbrainsAnnotations", "org.jetbrains:annotations:17.0.0")

            library("junitBom", "org.junit:junit-bom:6.0.3")
            library("junitJupiter", "org.junit.jupiter:junit-jupiter:6.0.3")
            library("junitPlatform", "org.junit.platform:junit-platform-launcher:6.0.3")
        }
    }
}

include("StorageManagerCore")
