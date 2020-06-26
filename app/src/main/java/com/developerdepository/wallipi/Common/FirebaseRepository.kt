package com.developerdepository.wallipi.Common

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var lastVisible: DocumentSnapshot? = null
    private val pageSize: Long = 8

    fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun queryWallpapers(): Task<QuerySnapshot> {

        if (lastVisible == null) {
            //Load First Page
            return firebaseFirestore
                .collection(Common.categoryName)
                .limit(10)
                .get()
        } else {
            return firebaseFirestore
                .collection(Common.categoryName)
                .startAfter(lastVisible!!)
                .limit(pageSize)
                .get()
        }
    }
}