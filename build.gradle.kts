plugins {
    id("org.jetbrains.intellij") version "1.4.0"
    id("org.jetbrains.kotlin.jvm") version "1.6.20-RC"
    java
}

group = "app.harry.intellij"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

intellij {
    version.set("2021.1")
}

tasks.patchPluginXml {
    sinceBuild.set("211.*")
    untilBuild.set("213.*")
}

tasks.test {
    useJUnitPlatform()
}