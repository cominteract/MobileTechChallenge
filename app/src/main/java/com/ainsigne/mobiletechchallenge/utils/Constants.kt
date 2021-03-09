package com.ainsigne.mobiletechchallenge.utils;

import android.os.Build


/**
 * Constants used throughout the app.
 */

val FAKE_BUILD = "fake"


/**
 * Check if running on emulator
 */
fun isEmulator(): Boolean {
    return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.PRODUCT.contains("sdk_google")
            || Build.PRODUCT.contains("google_sdk")
            || Build.PRODUCT.contains("sdk")
            || Build.PRODUCT.contains("sdk_x86")
            || Build.PRODUCT.contains("vbox86p")
            || Build.PRODUCT.contains("emulator")
            || Build.PRODUCT.contains("simulator"))
}


/**
 * movie id serialization key
 */
const val movieId = "movie_id"
/**
 * x axis serialization key for getting current viewholder x position
 */
const val xAxis = "x_axis"
/**
 * y axis serialization key for getting current viewholder y position
 */
const val yAxis = "y_axis"
/**
 * width serialization key for getting current viewholder width
 */
const val tWidth = "width"
/**
 * height serialization key for getting current viewholder height
 */
const val tHeight = "height"

/**
 * registered api key from omdb api
 */
const val movieDbApiKey = "227966dc"
/**
 * database name for use in building room db
 */
const val DATABASE_NAME = "omdb-movies-db"

/**
 * omdb base url for its api
 */
const val BASE_API = "http://www.omdbapi.com/"