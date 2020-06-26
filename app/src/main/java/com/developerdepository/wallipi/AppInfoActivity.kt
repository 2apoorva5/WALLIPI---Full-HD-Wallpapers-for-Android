package com.developerdepository.wallipi

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app_info.*
import maes.tech.intentanim.CustomIntent

class AppInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)

        close.setOnClickListener(View.OnClickListener { view: View? -> onBackPressed() })

        try {
            val packageInfo =
                applicationContext.packageManager.getPackageInfo(packageName, 0)
            val version = packageInfo.versionName
            app_info_version_name.setText(String.format("Version %s", version))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this@AppInfoActivity, "up-to-bottom")
    }
}