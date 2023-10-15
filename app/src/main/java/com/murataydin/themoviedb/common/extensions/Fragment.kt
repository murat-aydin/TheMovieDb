package com.murataydin.themoviedb.common.extensions

import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

fun Fragment.resColor(@ColorRes colorRes: Int) =
    ResourcesCompat.getColor(resources, colorRes, null)
