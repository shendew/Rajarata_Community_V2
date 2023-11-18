plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.thedev.rajaratacommunity"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.thedev.rajaratacommunity"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.android.material:material:1.3.0-alpha03")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")

    val lottieVersion = "3.4.0"
    implementation ("com.airbnb.android:lottie:$lottieVersion")

    implementation ( "com.github.skydoves:elasticviews:2.1.0")

    implementation ("com.google.firebase:firebase-auth:19.4.0")
    implementation ("com.google.android.gms:play-services-auth:18.1.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("io.github.pilgr:paperdb:2.7.2")

    implementation ("com.android.volley:volley:1.2.1")
    implementation ("org.jsoup:jsoup:1.14.3")


    //implementation ("org.sufficientlysecure:html-textview:4.0")
    implementation ("com.github.sunny52525:htmlView:0.1")
    implementation ("org.jsoup:jsoup:1.14.3")

    implementation ("androidx.work:work-runtime:2.7.1")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")

    implementation ("androidx.work:work-runtime:2.8.1")








//    val fragment_version = "1.6.1"
//
//    // Java language implementation
//    implementation("androidx.fragment:fragment:$fragment_version")
//
//    implementation ("org.jetbrains:annotations-java5:21.0.1")

}