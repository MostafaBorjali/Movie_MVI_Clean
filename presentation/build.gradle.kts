import constants.AndroidConfig.PRESENTATION_NAMESPACE

plugins {
    id(GradlePluginId.ANDROID_LIB)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
    id(GradlePluginId.SAFE_ARGS)
}

android {
    namespace = PRESENTATION_NAMESPACE
}

dependencies {

    implementation(LibraryDependency.appcompat)
    implementation(LibraryDependency.material_design)
    implementation(LibraryDependency.constraint_layout)
    implementation(LibraryDependency.legacy_support_v4)

    // --Android architecture component
    //implementation(LibraryDependency.lifecycle_extensions)
    // --Lifecycle
    implementation(LibraryDependency.lifecycle_runtime)
    implementation(LibraryDependency.lifecycle_reactive_streams)
    implementation(LibraryDependency.lifecycle_viewmodel)
    implementation(LibraryDependency.lifecycle_livedata)
    kapt(LibraryDependency.AnnotationProcessing.hilt_android_compiler)
    // --Hilt
    kapt(LibraryDependency.AnnotationProcessing.hilt_android_compiler)
    // --navigation
    implementation(LibraryDependency.navigation_fragment)
    implementation(LibraryDependency.navigation_ui)
    // Glide
    implementation(LibraryDependency.glide)
    kapt(LibraryDependency.AnnotationProcessing.glide_compiler)

    // --DOMAIN Module
    implementation(project(ModulesDependency.DOMAIN))

}