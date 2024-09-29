package com.poptato.build_logic.plugin

import com.poptato.build_logic.configureAndroidCommonPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.apply("com.android.library")
        configureAndroidCommonPlugin()
    }
}