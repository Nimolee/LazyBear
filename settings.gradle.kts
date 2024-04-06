pluginManagement {
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

rootProject.name = "LazyBear"
include(":app")
include(":modules:data:server")
include(":modules:data:tmdb_api")
include(":modules:data:tmdb_api_impl")
include(":modules:data:preferences")
include(":modules:data:preferences_impl")
include(":modules:utils:log")
include(":modules:utils:localization")
include(":modules:ui:advice")
include(":modules:ui:gallery")
