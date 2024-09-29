plugins {
    id("poptato.android.application")
    id("poptato.android.hilt")
}

android {
    namespace = "com.poptato.app"
}

dependencies {
    implementation(projects.feature)
    implementation(projects.domain)
    implementation(projects.core)
}