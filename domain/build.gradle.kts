plugins {
    id("poptato.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.core)

    implementation(libs.coroutines.core)
    implementation(libs.hilt.core)
}