plugins {
    id("poptato.android.application")
    id("poptato.android.hilt")
    id("poptato.android.kotlin")
}

android {
    namespace = "com.poptato.app"
}

dependencies {
    implementation(projects.feature)
    implementation(projects.domain)
    implementation(projects.core)
    implementation(projects.data)
}