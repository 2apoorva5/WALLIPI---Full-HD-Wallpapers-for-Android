package com.developerdepository.wallipi

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.developerdepository.wallipi.Common.Common
import com.developerdepository.wallipi.Common.FirebaseRepository
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.skydoves.powermenu.CircularEffect
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.fragment_wallpapers_category.*
import maes.tech.intentanim.CustomIntent

class WallpapersCategoryFragment : Fragment() {

    private val firebaseRepository = FirebaseRepository()
    private var navController: NavController? = null
    private var powerMenu: PowerMenu? = null

    private var mAppUpdateManager: AppUpdateManager? = null
    private var installStateUpdatedListener: InstallStateUpdatedListener? = null
    private val RC_APP_UPDATE = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpapers_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        AppRate.with(activity)
            .setInstallDays(1)
            .setLaunchTimes(3)
            .setRemindInterval(1)
            .setShowLaterButton(true)
            .setShowNeverButton(false)
            .monitor()

        AppRate.showRateDialogIfMeetsConditions(activity)

        menu.setOnClickListener {
            powerMenu = PowerMenu.Builder(requireContext())
                .addItem(PowerMenuItem("Privacy Policy", false))
                .addItem(PowerMenuItem("Rate WalLipi", false))
                .addItem(PowerMenuItem("Share WalLipi", false))
                .addItem(PowerMenuItem("App Info", false))
                .setCircularEffect(CircularEffect.BODY)
                .setMenuRadius(8f)
                .setMenuShadow(8f)
                .setTextColor(resources.getColor(R.color.textMenuColor))
                .setTextSize(16)
                .setTextGravity(Gravity.CENTER)
                .setMenuColor(resources.getColor(R.color.colorView))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setTextTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
                .build()

            powerMenu!!.showAsDropDown(menu)
        }

        val animals_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fanimals.jpg?alt=media&token=7487469f-3c18-45c6-8fac-9bed7f7d3b99"
        val architecture_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Farchitecture.jpg?alt=media&token=23764d1f-4154-4207-aaa2-5b72749c4a6a"
        val arts_culture_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Farts.jpg?alt=media&token=0ceb0a75-42e7-4ce2-a185-d2686ae6f07e"
        val business_work_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fwork.jpg?alt=media&token=7495f8e0-d17b-4fb9-b3ca-422ca8e363d2"
        val cars_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fcars.jpg?alt=media&token=f3425b62-aef5-4257-b7bb-16a7b8362dc6"
        val cities_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fcities.jpeg?alt=media&token=afb589ee-86b0-4738-a4c5-a024a2e51d65"
        val experimental_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fexperimental.jpg?alt=media&token=4da8d366-155b-49ba-9b38-05bd2194db09"
        val marvel_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fmarvel.jpg?alt=media&token=cd3ec0d6-63c4-4c76-938f-e330bd1c771e"
        val minimalist_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fminimal.jpg?alt=media&token=91dd90b3-9b31-4831-b5b7-34e1d1c01991"
        val nature_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fnature.jpg?alt=media&token=b60c0e86-b2dd-4266-a514-f9d43a64f62f"
        val people_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fpeople.jpg?alt=media&token=47fd087c-a0f0-4cc3-9128-2102f7d103a4"
        val space_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fspace.jpg?alt=media&token=2b114b11-ce85-45d6-b8de-5efaee21db00"
        val spirituality_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fspirituality.jpg?alt=media&token=6ab5ec15-46a9-4724-93fa-8093c87adb24"
        val sports_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fsports.jpg?alt=media&token=31bdad33-2e20-479f-b20e-3854720cd925"
        val technology_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Ftechnology.jpg?alt=media&token=24f4cc14-3aad-4477-b21b-5b2fd0551e03"
        val texture_pattern_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Ftexture.jpg?alt=media&token=3cfc5967-a67a-4c7f-a811-6905e3eed768"
        val travel_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Ftravel.jpg?alt=media&token=83c0daf2-66c8-4ade-a225-3dc42e43c672"
        val miscellaneous_img =
            "https://firebasestorage.googleapis.com/v0/b/wallipi-2501.appspot.com/o/CategoryImgs%2Fmiscellaneous.jpg?alt=media&token=83b5b4a3-64b7-49f2-a6a6-f7f232647da7"

        if (firebaseRepository.getUser() == null) {
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_registerFragment)
        }

        Glide.with(requireActivity().applicationContext).load(animals_img).centerCrop()
            .into(category_animals_img)
        Glide.with(requireActivity().applicationContext).load(architecture_img).centerCrop()
            .into(category_architecture_img)
        Glide.with(requireActivity().applicationContext).load(arts_culture_img).centerCrop()
            .into(category_arts_culture_img)
        Glide.with(requireActivity().applicationContext).load(business_work_img).centerCrop()
            .into(category_business_work_img)
        Glide.with(requireActivity().applicationContext).load(cars_img).centerCrop()
            .into(category_cars_img)
        Glide.with(requireActivity().applicationContext).load(cities_img).centerCrop()
            .into(category_cities_img)
        Glide.with(requireActivity().applicationContext).load(experimental_img).centerCrop()
            .into(category_experimental_img)
        Glide.with(requireActivity().applicationContext).load(marvel_img).centerCrop()
            .into(category_marvel_img)
        Glide.with(requireActivity().applicationContext).load(minimalist_img).centerCrop()
            .into(category_minimalist_img)
        Glide.with(requireActivity().applicationContext).load(nature_img).centerCrop()
            .into(category_nature_img)
        Glide.with(requireActivity().applicationContext).load(people_img).centerCrop()
            .into(category_people_img)
        Glide.with(requireActivity().applicationContext).load(space_img).centerCrop()
            .into(category_space_img)
        Glide.with(requireActivity().applicationContext).load(spirituality_img).centerCrop()
            .into(category_spirituality_img)
        Glide.with(requireActivity().applicationContext).load(sports_img).centerCrop()
            .into(category_sports_img)
        Glide.with(requireActivity().applicationContext).load(technology_img).centerCrop()
            .into(category_technology_img)
        Glide.with(requireActivity().applicationContext).load(texture_pattern_img).centerCrop()
            .into(category_texture_patterns_img)
        Glide.with(requireActivity().applicationContext).load(travel_img).centerCrop()
            .into(category_travel_img)
        Glide.with(requireActivity().applicationContext).load(miscellaneous_img).centerCrop()
            .into(category_miscellaneous_img)

        category_animals.setOnClickListener {
            Common.categoryName = "Animals"
            Common.wallpaperListTitle = "Animals"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_architecture.setOnClickListener {
            Common.categoryName = "Architecture"
            Common.wallpaperListTitle = "Architecture"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_arts_culture.setOnClickListener {
            Common.categoryName = "Arts"
            Common.wallpaperListTitle = "Arts & Culture"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_business_work.setOnClickListener {
            Common.categoryName = "Business"
            Common.wallpaperListTitle = "Business & Work"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_cars.setOnClickListener {
            Common.categoryName = "Cars"
            Common.wallpaperListTitle = "Cars"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_cities.setOnClickListener {
            Common.categoryName = "Cities"
            Common.wallpaperListTitle = "Cities"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_experimental.setOnClickListener {
            Common.categoryName = "Experimental"
            Common.wallpaperListTitle = "Experimental"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_marvel.setOnClickListener {
            Common.categoryName = "Marvel"
            Common.wallpaperListTitle = "Marvel"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_minimalist.setOnClickListener {
            Common.categoryName = "Minimalist"
            Common.wallpaperListTitle = "Minimalist"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_nature.setOnClickListener {
            Common.categoryName = "Nature"
            Common.wallpaperListTitle = "Nature"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_people.setOnClickListener {
            Common.categoryName = "People"
            Common.wallpaperListTitle = "People"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_space.setOnClickListener {
            Common.categoryName = "Space"
            Common.wallpaperListTitle = "Space"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_spirituality.setOnClickListener {
            Common.categoryName = "Spirituality"
            Common.wallpaperListTitle = "Spirituality"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_sports.setOnClickListener {
            Common.categoryName = "Sports"
            Common.wallpaperListTitle = "Sports"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_technology.setOnClickListener {
            Common.categoryName = "Technology"
            Common.wallpaperListTitle = "Technology"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_texture_patterns.setOnClickListener {
            Common.categoryName = "Texture"
            Common.wallpaperListTitle = "Texture & Patterns"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_travel.setOnClickListener {
            Common.categoryName = "Travel"
            Common.wallpaperListTitle = "Travel"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }

        category_miscellaneous.setOnClickListener {
            Common.categoryName = "Miscellaneous"
            Common.wallpaperListTitle = "Miscellaneous"
            navController!!.navigate(R.id.action_wallpapersCategoryFragment_to_wallpapersListFragment)
        }
    }

    private val onMenuItemClickListener: OnMenuItemClickListener<PowerMenuItem?> =
        OnMenuItemClickListener<PowerMenuItem?> { position, item ->
            powerMenu!!.selectedPosition = position
            if (powerMenu!!.selectedPosition == 0) {
                powerMenu!!.dismiss()
                val privacyPolicyUrl =
                    "https://developerdepository.wixsite.com/wallipi-policies"
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
                startActivity(browserIntent)
            } else if (powerMenu!!.selectedPosition == 1) {
                powerMenu!!.dismiss()
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + requireActivity().packageName)
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                        )
                    )
                }
            } else if (powerMenu!!.selectedPosition == 2) {
                powerMenu!!.dismiss()
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "WalLipi - Scroll &#8226; Select &#8226; Set"
                )
                val app_url =
                    " https://play.google.com/store/apps/details?id=" + requireActivity().packageName
                shareIntent.putExtra(Intent.EXTRA_TEXT, app_url)
                startActivity(Intent.createChooser(shareIntent, "Share via"))
            } else if (powerMenu!!.selectedPosition == 3) {
                powerMenu!!.dismiss()
                startActivity(Intent(activity, AppInfoActivity::class.java))
                CustomIntent.customType(activity, "bottom-to-up")
            }
        }


    override fun onStart() {
        super.onStart()

        mAppUpdateManager = AppUpdateManagerFactory.create(context)

        installStateUpdatedListener =
            InstallStateUpdatedListener { state ->
                if (state.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                } else if (state.installStatus() == InstallStatus.INSTALLED) {
                    if (mAppUpdateManager != null) {
                        mAppUpdateManager!!.unregisterListener(installStateUpdatedListener)
                    }
                } else {
                    Log.i(
                        ContentValues.TAG,
                        "InstallStateUpdatedListener: state: " + state.installStatus()
                    )
                }
            }

        mAppUpdateManager!!.registerListener(installStateUpdatedListener)

        mAppUpdateManager!!.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    try {
                        mAppUpdateManager!!.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            activity,
                            RC_APP_UPDATE
                        )
                    } catch (e: SendIntentException) {
                        e.printStackTrace()
                    }
                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                } else {
                    Log.e(
                        ContentValues.TAG,
                        "checkForAppUpdateAvailability: something else"
                    )
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != Activity.RESULT_OK) {
                Log.e(ContentValues.TAG, "onActivityResult: app download failed")
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        val snackbar = Snackbar.make(
            requireActivity().findViewById(R.id.coordinatorLayout_main),
            "New update is ready!",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(
            "Install"
        ) { view: View? ->
            if (mAppUpdateManager != null) {
                mAppUpdateManager!!.completeUpdate()
            }
        }
        snackbar.setActionTextColor(resources.getColor(R.color.colorPrimary))
        snackbar.show()
    }

    override fun onStop() {
        super.onStop()
        if (mAppUpdateManager != null) {
            mAppUpdateManager!!.unregisterListener(installStateUpdatedListener)
        }
    }
}