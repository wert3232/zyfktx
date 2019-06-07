@file:Suppress("NOTHING_TO_INLINE")

package android.zyf.dsl.view

import android.app.Activity
import android.app.Service
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.internals.AnkoInternals.createAnkoContext

inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T
inline fun <reified T : View> Fragment.findOptional(id: Int): T? = view?.findViewById(id) as? T

fun Fragment.UI(init: AnkoContext<Fragment>.() -> Unit) = createAnkoContext(requireActivity(), init)

inline fun <T : Any> Fragment.configuration(
    screenSize: ScreenSize? = null,
    density: ClosedRange<Int>? = null,
    language: String? = null,
    orientation: Orientation? = null,
    long: Boolean? = null,
    fromSdk: Int? = null,
    sdk: Int? = null,
    uiMode: UiMode? = null,
    nightMode: Boolean? = null,
    rightToLeft: Boolean? = null,
    smallestWidth: Int? = null,
    init: () -> T
): T? {
    val act = activity
    return if (act != null) {
        if (AnkoInternals.testConfiguration(
                act, screenSize, density, language, orientation, long,
                fromSdk, sdk, uiMode, nightMode, rightToLeft, smallestWidth
            )
        ) init() else null
    } else null
}

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}

inline fun Fragment.dip(value: Int): Int = requireActivity().dip(value)

inline fun Fragment.dip(value: Float): Int = requireActivity().dip(value)

inline fun Fragment.sp(value: Int): Int = requireActivity().sp(value)

inline fun Fragment.sp(value: Float): Int = requireActivity().sp(value)

inline fun Fragment.px2dip(px: Int): Float = requireActivity().px2dip(px)

inline fun Fragment.px2sp(px: Int): Float = requireActivity().px2sp(px)

inline fun Fragment.dimen(resource: Int): Int = requireActivity().dimen(resource)

fun Fragment.browse(url: String, newTask: Boolean = false): Boolean = requireActivity().browse(url, newTask)

fun Fragment.share(text: String, subject: String = ""): Boolean = requireActivity().share(text, subject)

fun Fragment.email(email: String, subject: String = "", text: String = ""): Boolean =
    requireActivity().email(email, subject, text)

fun Fragment.makeCall(number: String): Boolean = requireActivity().makeCall(number)

fun Fragment.sendSMS(number: String, text: String = ""): Boolean = requireActivity().sendSMS(number, text)

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStartActivity(requireActivity(), T::class.java, params)
}

inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) {
    startActivityForResult(AnkoInternals.createIntent(requireActivity(), T::class.java, params), requestCode)
}

inline fun <reified T : Service> Fragment.startService(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStartService(requireActivity(), T::class.java, params)
}

inline fun <reified T : Service> Fragment.stopService(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStopService(requireActivity(), T::class.java, params)
}

inline fun <reified T : Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent =
    AnkoInternals.createIntent(requireActivity(), T::class.java, params)

inline fun Fragment.toast(textResource: Int) = requireActivity().toast(textResource)

inline fun Fragment.toast(text: CharSequence) = requireActivity().toast(text)

inline fun Fragment.longToast(textResource: Int) = requireActivity().longToast(textResource)

inline fun Fragment.longToast(text: CharSequence) = requireActivity().longToast(text)

inline fun Fragment.selector(
    title: CharSequence? = null,
    items: List<CharSequence>,
    noinline onClick: (DialogInterface, Int) -> Unit
): Unit = requireActivity().selector(title, items, onClick)

inline fun Fragment.alert(
    message: String,
    title: String? = null,
    noinline init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = requireActivity().alert(message, title, init)

inline fun Fragment.alert(
    message: Int,
    title: Int? = null,
    noinline init: (AlertBuilder<DialogInterface>.() -> Unit)? = null
) = requireActivity().alert(message, title, init)

inline fun Fragment.alert(noinline init: AlertBuilder<DialogInterface>.() -> Unit) = requireActivity().alert(init)

inline val Fragment.defaultSharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(activity)