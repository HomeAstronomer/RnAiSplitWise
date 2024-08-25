package com.example.aisplitwise.ui
import android.content.Context
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle

@Composable
fun FragmentScreen(
  modifier: Modifier = Modifier,
  activity: AppCompatActivity,
  fragment: Fragment,
  fragmentView: FragmentContainerView?,
  viewHandle: (view: View) -> Unit = {}
) {
  if (!fragment.isAdded && activity.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
    val containerId = rememberSaveable { View.generateViewId() }

    AndroidView(
      modifier = modifier,
      factory = { context ->
        fragmentView ?: FragmentContainerView(context).apply {
          id = containerId
          activity.supportFragmentManager.commit(allowStateLoss = false) {
            replace(containerId, fragment, fragment::class.java.name)
          }
        }
      },
      update = viewHandle
    )

    DisposableEffect(Unit) {
      onDispose {
        val fragmentManager = activity.supportFragmentManager
        fragmentManager.findFragmentById(containerId)?.let { existingFragment ->
          if (!fragmentManager.isStateSaved) {
            fragmentManager.commit { remove(existingFragment) }
          }
        }
      }
    }
  }
}