package android.zyf.ktx

import android.os.Bundle
import androidx.annotation.IdRes
import android.view.View
import androidx.navigation.*
import java.lang.ref.WeakReference
import timber.log.Timber
import android.zyf.R
fun NavDestination.matchDestination(@IdRes destId: Int): Boolean {
    var currentDestination = this
    while (currentDestination.id != destId && currentDestination.parent != null) {
        currentDestination = currentDestination.parent!!
    }
    return currentDestination.id == destId
}

fun View.findNavController() = Navigation.findNavController(this)
fun NavController.navigateToStartDestination() {
    val options = NavOptions.Builder()
        .apply {
            setLaunchSingleTop(true)
            setEnterAnim(R.anim.alpha_in)
            setExitAnim(R.anim.alpha_out)
            setPopEnterAnim(R.anim.alpha_in)
            setPopExitAnim(R.anim.alpha_out)
            setPopUpTo(graph.startDestination, true)
        }.build()
    this.navigate(graph.startDestination, null, options)
}

/*
fun NavController.setupWithNavController(vararg radioTexts: RadioText) {
    radioTexts.apply {

    }
    Timber.e("$radioTexts ${radioTexts.size}")
    val weakReference = WeakReference(radioTexts)
    val options = NavOptions.Builder()
        .apply {
            setLaunchSingleTop(true)
            setEnterAnim(R.anim.alpha_in)
            setExitAnim(R.anim.alpha_out)
            setPopEnterAnim(R.anim.alpha_in)
            setPopExitAnim(R.anim.alpha_out)
            setPopUpTo(graph.startDestination, true)

        }.build()
    radioTexts.forEach {
        it.setOnClickListener {
            val destinationId = this@setupWithNavController.currentDestination?.id ?: 0
            if (it.id != destinationId) {
                this.navigate(it.id, null, options)
            }
        }
    }
    this.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
            //val views = weakReference.get()
            val views = radioTexts
            Timber.e("onNavigated")
            if (views == null) {
                Timber.e("weakReference null")
                controller.removeOnDestinationChangedListener(this)
            } else {
                for (rt in views) {
                    if (destination.matchDestination(rt.id)) {
                        val index = rt.viewIndex
                        views.forEach {
                            it.selectIndex = index
                        }
                        break
                    }
                }
            }
        }
    })
}*/
