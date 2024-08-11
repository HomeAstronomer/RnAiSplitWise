package com.example.aisplitwise

import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultReactNativeHost
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AISplitWIseApplication() : Application(),ReactApplication {

    override val reactNativeHost: ReactNativeHost =

        object : DefaultReactNativeHost(this) {

            override fun getPackages(): List<ReactPackage> {
                val packages = PackageList(this).packages.toMutableList()
                return packages
            }

            override fun getJSMainModuleName(): String {
                return "index"
            }

           

            override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

            override val isNewArchEnabled: Boolean = true

            override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED

           

        }
}