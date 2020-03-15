package com.technofreak.projectv12.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.technofreak.projectv12.models.Albums
import com.technofreak.projectv12.repositories.Albumrepository

class MainViewModel: ViewModel(
) {
    var mAlbums:ArrayList<Albums>?=null

    lateinit var mRepo:Albumrepository
     fun init(activity: Activity)
     {
         if (mAlbums != null) {
             return
         }

         mRepo= Albumrepository()
         mRepo.init(activity)
         mAlbums= this.mRepo.loadAllImages()
     }

}
