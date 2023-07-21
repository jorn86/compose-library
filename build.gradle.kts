plugins {
    id("org.jetbrains.compose") version "1.4.1"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    api(compose.desktop.currentOs)
    api(compose.materialIconsExtended)

    api("com.google.guava:guava:32.1.1-jre")

    api("org.slf4j:slf4j-simple:2.0.6")
}
