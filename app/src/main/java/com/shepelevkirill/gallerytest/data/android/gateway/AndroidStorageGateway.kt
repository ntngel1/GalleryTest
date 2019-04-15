package com.shepelevkirill.gallerytest.data.android.gateway

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.shepelevkirill.gallerytest.domain.gateway.StorageGateway
import io.reactivex.Single
import java.io.File
import java.net.URI

class AndroidStorageGateway(private val context: Context) : StorageGateway {

    override fun getPhotoFile(uri: URI): Single<File> = Single.create { emitter ->
        val androidUri = Uri.parse(uri.toString())
        var path: String?
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(androidUri, filePathColumn, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            path = cursor.getString(columnIndex)
            cursor.close()
        } else {
            path = null
        }

        emitter.onSuccess(File(path))
    }

    override fun getPhotoFile(uri: String): Single<File> = Single.defer {
        val uri = URI.create(uri)
        getPhotoFile(uri)
    }
}