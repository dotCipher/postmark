import groovy.lang.Closure
import netflix.nebula.dependency.recommender.RecommendationStrategies

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.41"))
    }
}

plugins {
    // https://plugins.gradle.org/plugin/com.palantir.git-version
    id("com.palantir.git-version") version "0.11.0"
    kotlin("jvm") version "1.3.41" apply false
    id("nebula.dependency-recommender") version "5.1.0"
}

allprojects {
    apply(plugin = "com.palantir.git-version")
    apply(plugin = "idea")

    repositories {
        mavenCentral()
        jcenter()
    }

    group = "io.dotcipher.postmark"
    // Use explicit cast for groovy call (see https://github.com/palantir/gradle-git-version/issues/105)
    version = (extensions.extraProperties.get("gitVersion") as? Closure<*>)?.call() ?: "dirty"

    // Configure dependency recommender
    val versionsFile = "versions.props"
    apply(plugin = "nebula.dependency-recommender")
    dependencyRecommendations {
        strategy = RecommendationStrategies.OverrideTransitives
        if (file(versionsFile).exists()) {
            propertiesFile(mapOf(Pair("file", project.file(versionsFile))))
        } else {
            propertiesFile(mapOf(Pair("file", project.rootProject.file(versionsFile))))
        }
    }

}
