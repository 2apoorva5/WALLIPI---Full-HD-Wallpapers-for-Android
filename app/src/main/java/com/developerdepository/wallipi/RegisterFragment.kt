package com.developerdepository.wallipi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        register_feedback?.setText(R.string.register_feedback_default)
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser == null) {
            register_feedback?.setText(R.string.register_feedback1)
            firebaseAuth.signInAnonymously().addOnCompleteListener {
                if (it.isSuccessful) {
                    register_feedback?.setText(R.string.register_feedback2)
                    navController?.navigate(R.id.action_registerFragment_to_wallpapersCategoryFragment)
                } else {
                    register_feedback?.setText(R.string.register_feedback3)
                }
            }
        } else {
            navController?.navigate(R.id.action_registerFragment_to_wallpapersCategoryFragment)
        }
    }
}