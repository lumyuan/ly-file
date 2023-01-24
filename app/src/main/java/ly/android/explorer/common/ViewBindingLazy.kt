package ly.android.explorer.common

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.core.BasePopupView
import ly.android.explorer.theme.Theme

inline fun <VB: ViewBinding> AppCompatActivity.bind(
    crossinline inflater: (LayoutInflater) -> VB
) = lazy {
    fullScreen(this)
    inflater(layoutInflater).apply {
        setContentView(this.root)
    }
}

inline fun <VB: ViewBinding> Fragment.bind(
    crossinline inflater: (LayoutInflater) -> VB
) = lazy {
    activity?.apply {
        fullScreen(this as AppCompatActivity)
    }
    inflater(layoutInflater)
}

inline fun <VB: ViewBinding> BasePopupView.bind(
    crossinline bind: (View) -> VB,
    @IdRes resId: Int
) = lazy {
    bind(findViewById(resId))
}

fun fullScreen(activity: AppCompatActivity){
    val darkMode = !Theme.isDarkMode()
    ImmersionBar.with(activity)
        .transparentStatusBar()
        .transparentNavigationBar()
        .statusBarDarkFont(darkMode)
        .navigationBarDarkIcon(darkMode)
        .keyboardEnable(true)
        .init()
}