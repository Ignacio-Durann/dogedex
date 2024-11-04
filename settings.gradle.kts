pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "androidx.navigation.safeargs") {
                useModule("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.1")
            }
        }
    }
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Dogedex"
include(":app")
