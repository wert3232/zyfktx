package android.zyf.ktx

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable

//layout_constraintHorizontal_bias
@BindingAdapter("img")
fun ImageView.setImg(@DrawableRes resId: Int?) {
    this.setImageResource(resId ?: 0)
}

@BindingAdapter("bindCardBackgroundColor")
fun androidx.cardview.widget.CardView.setBindCardBackgroundColor(cardBackGroundColor: Int) {
    this.setCardBackgroundColor(cardBackGroundColor)
}

inline fun BaseObservable.onChanged(crossinline callback: (Observable, Int) -> Unit) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            callback(sender, propertyId)
        }
    })
}