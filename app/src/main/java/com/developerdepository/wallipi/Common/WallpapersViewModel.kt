package com.developerdepository.wallipi.Common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

class WallpapersViewModel : ViewModel() {

    private val firebaseRepository: FirebaseRepository = FirebaseRepository()

    private val wallpapersList: MutableLiveData<List<WallpapersModel>> by lazy {
        MutableLiveData<List<WallpapersModel>>().also {
            loadWallpapersData()
        }
    }

    fun getWallpapersList(): LiveData<List<WallpapersModel>> {
        return wallpapersList
    }

    fun loadWallpapersData() {
        firebaseRepository.queryWallpapers().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                if (result!!.isEmpty) {
                    //No more results
                } else {
                    //Results ready to be loaded
                    if (wallpapersList.value == null) {
                        wallpapersList.value = result.toObjects(WallpapersModel::class.java)
                    } else {
                        wallpapersList.value =
                            wallpapersList.value!!.plus(result.toObjects(WallpapersModel::class.java))
                    }

                    val lastItem: DocumentSnapshot = result.documents[result.size() - 1]
                    firebaseRepository.lastVisible = lastItem
                }
            } else {
                Log.d("VIEW_MODEL_LOG", "Error : ${it.exception!!.message}")
            }
        }
    }
}