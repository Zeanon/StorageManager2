plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.slf4j)
    testImplementation(libs.logback)
    compileOnly(libs.jetbrainsAnnotations)

    testImplementation(platform(libs.junitBom))
    testImplementation(libs.junitJupiter)
    testRuntimeOnly(libs.junitPlatform)
}

tasks.test {
    useJUnitPlatform()
}
