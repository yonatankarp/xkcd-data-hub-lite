import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.spotless) apply true
}

allprojects {
    apply(plugin = "com.diffplug.spotless")

    repositories {
        mavenCentral()
    }

    afterEvaluate {
        plugins.withId("com.diffplug.spotless") {
            configure<com.diffplug.gradle.spotless.SpotlessExtension> {
                kotlin {
                    ktlint()
                    trimTrailingWhitespace()
                    target("src/**/*.kt")
                }
                kotlinGradle {
                    ktlint()
                    trimTrailingWhitespace()
                    target("**/*.kts")
                }
                flexmark {
                    flexmark()
                    trimTrailingWhitespace()
                    target("**/*.md")
                }
            }
        }
    }
}

subprojects {
    group = "com.xkcddatahub.lite"
    version = "0.0.1"

    afterEvaluate {
        tasks.withType(Test::class) {
            useJUnitPlatform()
        }

        tasks.withType(KotlinCompile::class.java) {
            dependsOn("spotlessCheck", ":copyGitHooks")
        }
    }
}

tasks.register("copyGitHooks", Copy::class) {
    from(".github/githooks")
    into(".git/hooks")
}
