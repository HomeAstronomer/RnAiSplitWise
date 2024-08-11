package com.example.aisplitwise.ui.screens

import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModel
import com.example.aisplitwise.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class Screen2ViewModel @Inject constructor(
    private val localDataSource: UserDao,
): ViewModel() {
    var reactScreenFragmentContainer:FragmentContainerView?=null

    fun setFragment(containerView:FragmentContainerView){
        reactScreenFragmentContainer=containerView
    }
}