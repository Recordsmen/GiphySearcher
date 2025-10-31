plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}
hilt {
    enableAggregatingTask = false
}
android {
    namespace = "chili.labs.giphysearcher"
    compileSdk = 36

    defaultConfig {
        applicationId = "chili.labs.giphysearcher"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.apply {
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            buildConfigField(
                "String",
                "GIPHY_API_KEY",
                "\"vuFtggLfVHb2xV425xjIBlulHqDWuYio\"",
            )
            buildConfigField(
                "String",
                "GIPHY_BASE_URL",
                "\"https://api.giphy.com/v1/\"",
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "GIPHY_API_KEY",
                "\"vuFtggLfVHb2xV425xjIBlulHqDWuYio\"",
            )
            buildConfigField(
                "String",
                "GIPHY_BASE_URL",
                "\"https://api.giphy.com/v1/\"",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    buildFeatures {
        compose = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.composed)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.bundles.network)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.androidx.compose.material.icons.extended)
    testImplementation(libs.junit)
}