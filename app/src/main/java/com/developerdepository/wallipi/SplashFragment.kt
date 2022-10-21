package com.developerdepository.wallipi

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    private var navController: NavController? = null

    private var topAnimation: Animation? = null
    private var bottomAnimation: Animation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        topAnimation = AnimationUtils.loadAnimation(activity, R.anim.start_top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(activity, R.anim.start_bottom_animation)
    }

    override fun onStart() {
        super.onStart()

        app_logo?.startAnimation(topAnimation)
        app_slogan?.startAnimation(bottomAnimation)
        app_powered_by1?.startAnimation(bottomAnimation)
        app_powered_by2?.startAnimation(bottomAnimation)

        Handler().postDelayed(Runnable {
            navController?.navigate(R.id.action_splashFragment_to_registerFragment)
        }, 5000)
    }
}