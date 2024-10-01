plugins {
    id("poptato.android.application")
    id("poptato.android.hilt")
    id("poptato.android.kotlin")
    id("poptato.retrofit")
}

android {
    namespace = "com.poptato.app"
}

dependencies {
    implementation(projects.feature)
    implementation(projects.domain)
    implementation(projects.core)
    implementation(projects.data)

    implementation(libs.gson)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.okhttp.urlconnection)
}