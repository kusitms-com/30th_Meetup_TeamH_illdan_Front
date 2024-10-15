import java.util.Properties

plugins {
    id("poptato.android.application")
    id("poptato.android.hilt")
    id("poptato.android.kotlin")
    id("poptato.retrofit")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.poptato.app"

    defaultConfig {
        val kakaoAppKey = properties.getProperty("KAKAO_APP_KEY")
        buildConfigField("String", "KAKAO_APP_KEY", "\"${properties.getProperty("KAKAO_APP_KEY")}\"")
        manifestPlaceholders["KAKAO_HOST_SCHEME"] = "kakao$kakaoAppKey"
        versionCode = 1
        versionName = "1.0"
    }
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

    // 카카오 로그인
    implementation(libs.kakao.auth)

    // ThreeTen
    implementation(libs.threeten)

    implementation(libs.hilt.core)
}