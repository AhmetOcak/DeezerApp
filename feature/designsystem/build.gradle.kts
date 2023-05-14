plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.designsystem"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(Libs.AndroidX.materialIconsExtended)

    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.AnroidX.junit)
    androidTestImplementation(Libs.Test.AnroidX.espresso)

    implementation(Libs.Accompanist.systemUiController)

    // Coil
    implementation(Libs.Coil.coil)
    implementation(Libs.Coil.coilCompose)
}