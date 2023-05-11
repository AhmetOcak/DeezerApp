pluginManagement {
    repositories {
        google()
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
rootProject.name = "DeezerApp"
include(":app")
include(":feature")
include(":feature:musicgenres")
include(":feature:designsystem")
include(":feature:ui")
include(":feature:favorites")
include(":feature:artists")
include(":feature:albumdetail")
include(":feature:artistdetail")
