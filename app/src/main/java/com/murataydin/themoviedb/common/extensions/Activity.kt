package com.murataydin.themoviedb.common.extensions

import android.app.Activity
import android.content.res.Configuration

fun Activity.isTablet(): Boolean {
    return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
}