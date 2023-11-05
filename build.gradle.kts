plugins {
    kotlin("multiplatform") version "1.9.10"
}

repositories {
    mavenCentral()
}

kotlin {
    linuxX64("native") {
        // linuxX64
        // mingwX64
        // macosX64
        binaries {
            executable()
        }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "8.4"
    distributionType = Wrapper.DistributionType.BIN
}
