import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.deezerapp"
    compileSdk = ConfigData.compileSdk

    defaultConfig {
        applicationId = "com.deezerapp"
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = BuildTypes.isMinifyEnabled
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ComposeOptions.kotlinCompilerExtensionVersion
    }
    fun Packaging.() {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.runtimeKtx)
    implementation(Libs.AndroidX.activityCompose)
    implementation(platform(Libs.AndroidX.composeBom))
    implementation(Libs.AndroidX.composeUi)
    implementation(Libs.AndroidX.composeUiGraphics)
    implementation(Libs.AndroidX.composeUiToolingPreview)
    implementation(Libs.AndroidX.composeMaterial3)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.AnroidX.junit)
    androidTestImplementation(Libs.Test.AnroidX.espresso)
    androidTestImplementation(platform(Libs.Test.AnroidX.composeBom))
    androidTestImplementation(Libs.Test.AnroidX.composeUiTest)

    debugImplementation(Libs.AndroidX.Debug.composeUiTooling)
    debugImplementation(Libs.AndroidX.Debug.composeUiManifest)

    // Hilt
    implementation(Libs.Hilt.daggerHilt)
    implementation(Libs.Hilt.navigationCompose)
    kapt(Libs.Hilt.compiler)

    // Navigation Animation
    implementation(Libs.Accompanist.navigationAnimation)

    implementation(project(":feature:musicgenres"))
    implementation(project(":feature:albumdetail"))
    implementation(project(":feature:artistdetail"))
    implementation(project(":feature:favorites"))
    implementation(project(":feature:artists"))
    implementation(project(":feature:designsystem"))
}