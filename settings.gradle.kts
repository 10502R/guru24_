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
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven (url = "https://devrepo.kakao.com/nexus/content/groups/public/")
        jcenter() // 사용 중지될 예정이지만, 필요한 경우 추가
    }
}

rootProject.name = "guru24"
include(":app")
