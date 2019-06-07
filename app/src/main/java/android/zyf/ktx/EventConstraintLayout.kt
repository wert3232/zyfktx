package android.zyf.ktx

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent

open class EventConstraintLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    var onEventTouchDown = {}
    var onEventTouchUp = {}
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        try {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    onEventTouchDown.invoke()
                }
                MotionEvent.ACTION_UP -> {
                    onEventTouchUp.invoke()
                }
            }
        } catch (e: Exception) {
            throw e
        }
        return super.dispatchTouchEvent(ev)
    }
}