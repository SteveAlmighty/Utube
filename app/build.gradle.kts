plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroidKsp)

}

android {
    namespace = "com.example.utube"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.utube"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug{
            buildConfigField("String", "BASE_URL", "\"https://www.googleapis.com/youtube/v3\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"https://www.googleapis.com/youtube/v3\"")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.room.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.coil)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.bundles.ktor)

    implementation (libs.androidx.hilt.navigation.compose)

    implementation (libs.core)


    implementation(libs.hilt)
    ksp(libs.hilt.compiler)




    implementation (libs.retrofit.v2110)
    implementation (libs.converter.gson)
    implementation (libs.com.squareup.okhttp3.okhttp)
    implementation (libs.logging.interceptor)


    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.play.services.auth)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.room.runtime)
    val roomVersion = "2.6.1" // Use the latest version

    implementation(libs.androidx.room.ktx) // For Kotlin extensions (optional but recommended)
    ksp(libs.androidx.room.compiler) // Use kapt for the annotation processor

    implementation(libs.sqlite.bundled)

    api(libs.koin.core)


}