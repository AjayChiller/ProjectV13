package com.technofreak.projectv12.models


import android.util.Log

data class Albums(var folderNames: String?, var imagePath: String?, var imgCount: Int, var isVideo: Boolean) {
    public fun display() {
        Log.i("DEBUG", "path = " + this.imagePath)

    }
    override fun equals(other: Any?): Boolean {
        return super.equals(other)

        if (javaClass != other?.javaClass)
            return false

        other as Albums
        if (imagePath != other.imagePath)
            return false


         }

    override fun toString(): String {
        return "Albums(folderNames='$folderNames', imagePath='$imagePath', imgCount='$imgCount')"
    }


}


