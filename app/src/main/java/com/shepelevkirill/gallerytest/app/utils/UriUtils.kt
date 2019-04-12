package com.shepelevkirill.gallerytest.app.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun Uri.getPath(context: Context): String? {
    var path: String?
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(this, filePathColumn, null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        path = cursor.getString(columnIndex)
        cursor.close()
    } else {
        path = null
    }

    return path
}