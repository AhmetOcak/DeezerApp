plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.musicgenres"
    compileSdk = ConfigData.compileSdk

    defaultConfig {
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = BuildTypes.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}

dependencies {

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.material)

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

    // Hilt
    implementation(Libs.Hilt.daggerHilt)
    implementation(Libs.Hilt.navigationCompose)
    kapt(Libs.Hilt.compiler)

    implementation(project(":feature:designsystem"))
    implementation(project(":feature:ui"))

    implementation(project(":domain:usecases"))

    implementation(project(":models"))
}