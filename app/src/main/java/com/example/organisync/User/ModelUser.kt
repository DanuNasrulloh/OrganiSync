package com.example.organisync.User

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
    var token: String,
    var isLogin: Boolean
): Parcelable