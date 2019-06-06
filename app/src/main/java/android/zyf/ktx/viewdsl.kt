@file:Suppress("NOTHING_TO_INLINE", "UNUSED_ANONYMOUS_PARAMETER")
package android.zyf.ktx

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatTextView

interface ViewItem
class Container(val context: Context, var view: ViewGroup? = null) : ViewItem {
    fun addChild(child: View) {
        view!!.addView(child)
    }
}

inline fun Context.layout(builder: Container.() -> ViewGroup): ViewGroup {
    val c = Container(this)
    return c.builder()
}

inline fun Container.verticalLayout(builder: Container.() -> Unit): LinearLayout {
    val viewGroup = LinearLayout(this.context).also {
        it.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).also {
            it.gravity = Gravity.CENTER
        }
        it.orientation = LinearLayout.VERTICAL
    }
    if (view == null) {
        view = viewGroup
    } else {
        view!!.addView(viewGroup)
    }
    Container(this.context, viewGroup).builder()
    return viewGroup
}

fun Container.textView(builder: AppCompatTextView.() -> Unit) {
    val item = AppCompatTextView(this.context)
    this.addChild(item)
    item.builder()
}

val Context.globalHeight: Int
    get() = kotlin.run {
        this as Activity
        this.window.decorView.height
    }
val Context.globalWidth: Int
    get() = kotlin.run {
        this as Activity
        this.window.decorView.height
    }
val Context.globalView: View
    get() = kotlin.run {
        this as Activity
        this.window.decorView
    }
inline fun TextView.setTextSizeRes(@DimenRes rid: Int) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, this.context.resources.getDimension(rid))
}

inline fun View.px(@DimenRes rid: Int): Int {
    return this.context.resources.getDimensionPixelOffset(rid)
}