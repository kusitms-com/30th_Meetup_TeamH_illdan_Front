plugins {
    id("poptato.android.feature")
    id("poptato.android.compose")
}

android {
    namespace = "com.poptato.mypage"

    defaultConfig {
        buildConfigField(type = "String", name = "VERSION_NAME", value = "\"${project.version}\"")
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)
    implementation(projects.core.ui)
    implementation(projects.designSystem)

    // 웹뷰
    implementation(libs.web.view)
}