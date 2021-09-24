package com.ramgdeveloper.ramglibrary.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
     val categoryName: String? = null,
     val categoryImage: String? = null
) : Parcelable
