package com.example.aisplitwise.ui.screens

import androidx.activity.compose.BackHandler
import com.example.aisplitwise.ui.FragmentScreen
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import com.example.aisplitwise.composeBack
import com.facebook.react.ReactActivity
import com.facebook.react.ReactApplication
import com.facebook.react.ReactFragment
import com.facebook.react.ReactInstanceEventListener
import com.facebook.react.bridge.ReactContext

@Composable

fun Screen2(screen2ViewModel: Screen2ViewModel, navController: NavHostController){
    val activity= LocalContext.current as AppCompatActivity
    val context= LocalContext.current
    ReactFragment.Builder().setComponentName("RnAiSplitWise")
//        .setLaunchOptions(getLaunchOptions("test message"))
        .build().let {reactFragment ->
            FragmentScreen(modifier= Modifier
                .fillMaxSize()
                .background(Color.DarkGray), activity =activity,
                fragment = reactFragment,
                fragmentView = screen2ViewModel.reactScreenFragmentContainer,
                viewHandle = {
                    if(it is FragmentContainerView){
                        screen2ViewModel.setFragment(it)
                    }
                })

        }


    BackHandler(enabled = true) {
        (context.applicationContext as ReactApplication).reactNativeHost.reactInstanceManager.onBackPressed()
    }



}
@Composable
fun Screen3(screen2ViewModel: Screen2ViewModel){
    val activity= LocalContext.current as AppCompatActivity
    ReactFragment.Builder().setComponentName("RnAiSplitWise")
//        .setLaunchOptions(getLaunchOptions("test message"))
        .build().let {reactFragment ->
            FragmentScreen(modifier= Modifier
                .fillMaxSize()
                .background(Color.DarkGray), activity =activity,
                fragment = reactFragment,
                fragmentView = screen2ViewModel.reactScreenFragmentContainer,
                viewHandle = {
                    if(it!=null && (it is FragmentContainerView)){
                        screen2ViewModel.setFragment(it)
                    }
                })

        }

}