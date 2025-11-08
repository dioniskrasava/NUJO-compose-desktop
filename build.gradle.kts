plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.11"
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "org.example.MainKt"

        nativeDistributions {
            packageName = "ComposeCounter"
            packageVersion = "1.0.0"
            description = "Простое приложение-счётчик"

            linux {
                appCategory = "Utility"
            }

            // Только AppImage для простоты
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.AppImage,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
        }
    }
}

kotlin {
    jvmToolchain(17)
}