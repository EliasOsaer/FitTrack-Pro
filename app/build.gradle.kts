
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
}

android {
    namespace = "be.howest.eliasosaer.fittrackpro"
    compileSdk = 35

    defaultConfig {
        applicationId = "be.howest.eliasosaer.fittrackpro"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_BASE_URL", "\"${project.findProperty("API_BASE_URL")}\"")
        buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY")}\"")

        buildConfigField("String", "AMQPusername", "\"${project.findProperty("AMQPusername")}\"")
        buildConfigField("String", "AMQPpassword", "\"${project.findProperty("AMQPpassword")}\"")
        buildConfigField("String", "AMQPurl", "\"${project.findProperty("AMQPurl")}\"")
        buildConfigField("String", "AMQPpublishtopic", "\"${project.findProperty("AMQPpublishtopic")}\"")
        buildConfigField("String", "AMQPsubscribetopic", "\"${project.findProperty("AMQPsubscribetopic")}\"")
        buildConfigField("String", "AMQPconsumertag", "\"${project.findProperty("AMQPconsumertag")}\"")

        buildConfigField("String", "MAPBOX_ACCESS_TOKEN", "\"${project.findProperty("MAPBOX_ACCESS_TOKEN")}\"")

    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    //week3 for testing navigation
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.navigation.testing)

    //week4 voor datastore
    implementation(libs.androidx.preferences.datastore)

    //week4 Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.play.services.location)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //week 4: Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //week5: Networking
    implementation(libs.coil.compose)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.rabbitmq.amqp.client)

    //week6: WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    //Week7: Mapbox
    implementation(libs.mapbox.android)
    implementation(libs.mapbox.extension.compose)

    //Testing
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    //Hilt and testing
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.work.testing)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}