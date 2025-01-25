import java.io.FileInputStream
import java.lang.System.load
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.guru24"
    compileSdk = 35

    packagingOptions {
        resources {
            excludes += ("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
        }
    }

    val properties = Properties().apply {
        load(FileInputStream(rootProject.file("local.properties")))
    }

    defaultConfig {
        applicationId = "com.example.guru24"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.add("arm64-v8a")
        }

        // KAKAO_MAP_KEY 추가
        buildConfigField("String", "KAKAO_MAP_KEY",
            "\"${properties.getProperty("KAKAO_MAP_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        // BuildConfig 사용 가능하도록 설정
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.kakao.maps.open:android:2.12.8")
    implementation(libs.identity.android.legacy) // Kakao Maps 라이브러리
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
#a