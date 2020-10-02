package com.tweedchristian.android.basketballscore

import android.app.Activity
import android.graphics.*
import kotlin.math.roundToInt


fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
    var inSampleSize = 1
    if(srcHeight > destHeight || srcWidth > destWidth){
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWidth
        val sampleScale = if(heightScale > widthScale) {
            heightScale
        } else {
            widthScale
        }
        inSampleSize = sampleScale.roundToInt()
    }

    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize
    return BitmapFactory.decodeFile(path, options)
}

fun getScaledBitmap(path: String, activity: Activity): Bitmap {
    /**Crashes my App*/
//    val metrics: WindowMetrics = activity.windowManager.currentWindowMetrics
//    // Gets all excluding insets
//    // Gets all excluding insets
//    val windowInsets = metrics.windowInsets
//    val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
//        WindowInsets.Type.navigationBars()
//                or WindowInsets.Type.displayCutout()
//    )
//
//    val insetsWidth: Int = insets.right + insets.left
//    val insetsHeight: Int = insets.top + insets.bottom
//
//    // Legacy size that Display#getSize reports
//
//    // Legacy size that Display#getSize reports
//    val bounds: Rect = metrics.bounds
//    val legacySize = Size(
//        bounds.width() - insetsWidth,
//        bounds.height() - insetsHeight
//    )
//
//    return getScaledBitmap(path, legacySize.width, legacySize.height)

    val size = Point()
    activity.windowManager.defaultDisplay.getSize(size)
    return getScaledBitmap(path, size.x, size.y)
}