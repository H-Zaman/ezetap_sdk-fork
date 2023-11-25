package com.plugin.ezetap.sdk.eze_sdk_flutter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.util.Base64
import android.view.View
import java.io.ByteArrayOutputStream


/**
@author TIJO THOMAS
@since 10/11/22
 */

fun Intent?.createJsonObject(): Any? {
    return this?.extras?.get("response")
}

fun Activity.loadBitmapBase64EncodedFromView(): String? {
    val view: View = this.window.decorView.findViewById(android.R.id.content)
    val bitmap = Bitmap.createBitmap(
        view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888
    )
    val c = Canvas(bitmap)
    view.draw(c)
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
    val byteFormat: ByteArray = stream.toByteArray()
    return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
}

fun isDeviceX990() : Boolean{
    return Build.MODEL.equals("X990", ignoreCase = true)
}
