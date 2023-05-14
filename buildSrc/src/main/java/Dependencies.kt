
object Libs {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.8.0"
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val material = "com.google.android.material:material:1.9.0"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val activityCompose = "androidx.activity:activity-compose:1.5.1"
        const val composeBom = "androidx.compose:compose-bom:2023.04.01"
        const val composeUi = "androidx.compose.ui:ui"
        const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeMaterial3 = "androidx.compose.material3:material3"
        const val materialIconsExtended = "androidx.compose.material:material-icons-extended"

        object Debug {
            const val composeUiTooling = "androidx.compose.ui:ui-tooling"
            const val composeUiManifest = "androidx.compose.ui:ui-test-manifest"
        }
    }

    object Test {
        const val junit = "junit:junit:4.13.2"

        object AnroidX {
            const val junit  = "androidx.test.ext:junit:1.1.5"
            const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
            const val composeBom = "androidx.compose:compose-bom:2022.10.00"
            const val composeUiTest = "androidx.compose.ui:ui-test-junit4"
        }
    }

    object Hilt {
        const val daggerHilt = "com.google.dagger:hilt-android:2.45"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val compiler = "com.google.dagger:hilt-compiler:2.45"
    }

    object Accompanist {
        const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:0.25.1"
        const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:0.23.1"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.3.0"
    }

    object Okhttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:4.10.0"
    }

    object Javax {
        const val inject = "javax.inject:javax.inject:1"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:2.1.0"
        const val coilCompose = "io.coil-kt:coil-compose:2.1.0"
        const val coilGif = "io.coil-kt:coil-gif:2.3.0"
    }
}