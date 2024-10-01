plugins {
    id("poptato.kotlin.jvm")
    id("poptato.kotlin.serialization")
    id("poptato.retrofit")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core)
}