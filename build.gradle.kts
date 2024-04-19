// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(GradlePluginId.VERSIONS_PLUGIN) version CoreVersion.VERSIONS_PLUGIN
}
buildscript {
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath(GradleClasspath.ANDROID_GRADLE)
        classpath(kotlin(GradleClasspath.KOTLIN_PlUGIN, version = CoreVersion.KOTLIN))
        classpath(GradleClasspath.HILT)
        classpath(GradleClasspath.SAFE_ARGS)

    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }


}

tasks.register("clean", Delete::class) {
    delete(project.layout.buildDirectory)
}
