package com.ramgdeveloper.ramglibrary.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
     val categoryName: String? = null,
     val categoryImage: String? = null,
     val bookName: String? = null,
     val bookDescription: String? = null
) : Parcelable
