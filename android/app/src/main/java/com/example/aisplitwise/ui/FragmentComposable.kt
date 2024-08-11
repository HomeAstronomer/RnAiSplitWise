package `in`.jfs.jiofinance.common.util.composefragment

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment

//region fragment composable construct...

//region exposing composable...


var currentSelectItemId = 0

var savedStateSparseArray = SparseArray<Fragment.SavedState>()





@Composable
fun FragmentScreen(
  modifier: Modifier,
  activity: AppCompatActivity,
  fragment: Fragment,
  fragmentView:FragmentContainerView?,
  viewHandle: (view: View) -> Unit = {}
) {
  if (fragment.isAdded.not()) {
    FragmentContainer(
      modifier = modifier,
      commit = fetchCommitFunction(
        fragment = fragment,
        tag = fragment::class.java.name,
        activity = activity
      ),
      fragmentView = fragmentView,
      viewHandle = { view ->
        viewHandle.invoke(view)
      }
    )
  }
}
//endregion

//region fragment compose digest...
@Composable
fun FragmentContainer(
  modifier: Modifier = Modifier,
  commit: FragmentTransaction.(containerId: Int) -> Unit,
  fragmentView: FragmentContainerView?,
  viewHandle: (view: View) -> Unit,
) {
  val localView = LocalView.current
  val parentFragment = remember(localView) {
    try {
      localView.findFragment<Fragment>()
    } catch (error: java.lang.Exception) {
      null
    }
  }
  val containerId by rememberSaveable { mutableStateOf(View.generateViewId()) }
  //The containerId was required for fragment navigation in JPB and UPI.
  val container = remember { mutableStateOf<FragmentContainerView?>(null) }
  val viewBlock: (Context) -> View = remember(localView) {
    { context ->
      FragmentContainerView(context)
        .apply { id = containerId }
        .also {
          val fragmentManager = parentFragment?.childFragmentManager
            ?: (context as? FragmentActivity)?.supportFragmentManager
          fragmentManager?.commit(allowStateLoss = true) { commit(it.id) }
          container.value = it
        }
    }
  }

  /**
  * Old implementation of Fragment rendering, commenting it for reference
  * */
//  AndroidView(
//    modifier = modifier,
//    factory = viewBlock,
//    update = { view: View -> viewHandle.invoke(view) }
//  )

  /**
   * New implementation of Fragment rendering for Vertical dashboard
   * */
  AndroidView(
    modifier = modifier,
    factory = { context ->
      fragmentView ?: loadFragmentView(context, localView, commit)
    },
    update = {
        view: View -> viewHandle.invoke(view)
    }
  )
  val localContext = LocalContext.current
  DisposableEffect(localView, localContext, container) {
    onDispose {
      val fragmentManager = parentFragment?.childFragmentManager
        ?: (localContext as? FragmentActivity)?.supportFragmentManager
      // Now find the fragment inflated via the FragmentContainerView
      val existingFragment = fragmentManager?.findFragmentById(container.value?.id ?: 0)
      if (existingFragment != null && !fragmentManager.isStateSaved) {
        // If the state isn't saved, that means that some state change
        // has removed this Composable from the hierarchy
        try {
          fragmentManager.commit {
            remove(existingFragment)
          }
        }catch (e : Exception){
          e.printStackTrace()
        }
      }
    }
  }
}
//endregion

//region fragment composable utils...
private fun fetchCommitFunction(
  fragment: Fragment,
  tag: String,
  activity: AppCompatActivity
): FragmentTransaction.(containerId: Int) -> Unit =
  {
    saveAndRetrieveFragment(activity, it, fragment)
    replace(it, fragment, tag)
  }

private fun saveAndRetrieveFragment(
    activity: AppCompatActivity,
    tabId: Int,
    fragment: Fragment
) {
  activity.run {
    val currentFragment = supportFragmentManager.findFragmentById(currentSelectItemId)
    if (currentFragment != null) {
      savedStateSparseArray.put(
        currentSelectItemId,
        supportFragmentManager.saveFragmentInstanceState(currentFragment)
      )
    }
    currentSelectItemId = tabId
    fragment.setInitialSavedState(savedStateSparseArray[currentSelectItemId])
  }
}
//endregion
//endregion



private var view: WebView? = null
fun loadView(context: Context): WebView {
  if (view == null) {
    view = WebView(context)
    //init your view here
  }
  return view!!
}

fun loadFragmentView(context: Context, view: View, commit: FragmentTransaction.(containerId: Int) -> Unit):FragmentContainerView{
  var fragmentContainerView:FragmentContainerView?=null
  if(fragmentContainerView==null) {
    val parentFragment = try {
      view.findFragment<Fragment>()
    } catch (error: java.lang.Exception) {
      null
    }

    val containerId = View.generateViewId()
    fragmentContainerView = FragmentContainerView(context)
      .apply { id = containerId }
      .also {
        val fragmentManager = parentFragment?.childFragmentManager
          ?: (context as? FragmentActivity)?.supportFragmentManager
        fragmentManager?.commit(allowStateLoss = true) { commit(it.id) }

        fragmentManager?.addFragmentOnAttachListener {
            _,_ ->
          (context as? AppCompatActivity)
            ?.savedStateRegistry
            ?.unregisterSavedStateProvider("android:support:fragments")
        }
      }

  }
  return fragmentContainerView
}