plugins {
    id("java")
}

group = "io.github.tomthebuddhacat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // JDA dependency
    implementation("net.dv8tion:JDA:6.7.0")

    // Configurate dependency
    implementation("org.spongepowered:configurate-hocon:4.2.0")
}

tasks.test {
    useJUnitPlatform()
}