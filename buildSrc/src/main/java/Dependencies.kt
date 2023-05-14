
object Libs {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.8.0"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val activityCompose = "androidx.activity:activity-compose:1.5.1"
        const val composeBom = "androidx.compose:compose-bom:2023.04.01"
        const val composeUi = "androidx.compose.ui:ui"
        const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeMaterial3 = "androidx.compose.material3:material3"

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
    }
}