package com.example.aisplitwise

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

class AISplitWiseNativePackages:ReactPackage {
    override fun createNativeModules(p0: ReactApplicationContext): MutableList<NativeModule> = mutableListOf(ReactNativeJSBridgeModule())


    override fun createViewManagers(p0: ReactApplicationContext): MutableList<ViewManager<View, ReactShadowNode<*>>> =
        mutableListOf()

}