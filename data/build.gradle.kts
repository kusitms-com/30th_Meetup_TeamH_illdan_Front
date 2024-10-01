plugins {
    id("poptato.android.feature")
    id("poptato.retrofit")
    id("poptato.android.hilt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core)

    implementation(libs.androidx.datastore)
}