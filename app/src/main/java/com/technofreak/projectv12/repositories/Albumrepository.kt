package com.technofreak.projectv12.repositories

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.technofreak.projectv12.models.Albums

class Albumrepository
{

    lateinit var listalbum:ArrayList<Albums>
    lateinit var temp: Activity

    fun init(activity: Activity)
    {
        temp=activity
    }
    fun loadAllImages(): ArrayList<Albums> {
        var imagesList = getAllShownImagesPath()
        for (i in imagesList)
        {
            i.display()
        }
        return imagesList
    }
    private fun getAllShownImagesPath(): ArrayList<Albums> {

        val uri: Uri
        val cursor: Cursor
        var cursorBucket: Cursor
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String? = null
        var albumsList = ArrayList<Albums>()

        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "MAX(datetaken) DESC"
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATA)
        cursor =
            temp.contentResolver.query(uri, projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)!!
        if (cursor != null) {
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data)
                Log.d("title_apps", "bucket name:" + cursor.getString(column_index_data))
                val selectionArgs = arrayOf("%" + cursor.getString(column_index_folder_name) + "%")
                val selection = MediaStore.Images.Media.DATA + " like ? "
                val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                cursorBucket = temp.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)!!
                Log.d("title_apps", "bucket size:" + cursorBucket.count)
                if (absolutePathOfImage != "" && absolutePathOfImage != null) {
                    listOfAllImages.add(absolutePathOfImage)
                    albumsList.add(Albums(cursor.getString(column_index_folder_name), absolutePathOfImage, cursorBucket.count, false))
                }
            }
        }
        return getListOfVideoFolders(albumsList)
    }
   // This function is resposible to read all videos from all folders.
    private fun getListOfVideoFolders(albumsList: ArrayList<Albums>): ArrayList<Albums> {
        var cursor: Cursor
        var cursorBucket: Cursor
        var uri: Uri
        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "MAX(datetaken) DESC"
        val column_index_album_name: Int
        val column_index_album_video: Int
        uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection1 = arrayOf(
            MediaStore.Video.VideoColumns.BUCKET_ID,
            MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATE_TAKEN,
            MediaStore.Video.VideoColumns.DATA)
        cursor = temp.contentResolver.query(uri, projection1, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)!!
        if (cursor != null) {
            column_index_album_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            column_index_album_video = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            while (cursor.moveToNext()) {
                Log.d("title_apps", "bucket video:" + cursor.getString(column_index_album_name))
                Log.d("title_apps", "bucket video:" + cursor.getString(column_index_album_video))
                val selectionArgs = arrayOf("%" + cursor.getString(column_index_album_name) + "%")
                val selection = MediaStore.Video.Media.DATA + " like ? "
                val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
                cursorBucket = temp.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)!!
                Log.d("title_apps", "bucket size:" + cursorBucket.count)
                albumsList.add(Albums(cursor.getString(column_index_album_name), cursor.getString(column_index_album_video), cursorBucket.count, true))
            }
        }
        return albumsList
    }

}