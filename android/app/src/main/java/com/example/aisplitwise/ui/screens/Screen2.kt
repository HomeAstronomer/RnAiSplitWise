package com.example.aisplitwise.ui.screens

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.FragmentContainerView
import com.facebook.react.ReactFragment
import `in`.jfs.jiofinance.common.util.composefragment.FragmentContainer
import `in`.jfs.jiofinance.common.util.composefragment.FragmentScreen

@Composable

fun Screen2(screen2ViewModel: Screen2ViewModel){
    val activity= LocalContext.current as AppCompatActivity
    ReactFragment.Builder().setComponentName("RnAiSplitWise")
//        .setLaunchOptions(getLaunchOptions("test message"))
        .build().let {reactFragment ->
            FragmentScreen(modifier= Modifier.fillMaxSize().background(Color.DarkGray), activity =activity,
                fragment = reactFragment,
                fragmentView = screen2ViewModel.reactScreenFragmentContainer,
                viewHandle = {
                    if(it!=null && (it is FragmentContainerView)){
                        screen2ViewModel.setFragment(it)
                    }
                })

        }

}