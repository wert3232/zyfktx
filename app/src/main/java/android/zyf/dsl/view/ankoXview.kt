@file:Suppress("NOTHING_TO_INLINE", "unused")

package android.zyf.dsl.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputType
import android.util.TypedValue
import android.view.*
import android.widget.*
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals.NO_GETTER
import org.jetbrains.anko.internals.AnkoInternals.noGetter
import kotlin.DeprecationLevel.ERROR
import org.jetbrains.anko.UI

class _LinearLayout(ctx: Context) : LinearLayout(ctx)

val matchParent: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT
val wrapContent: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

var ViewGroup.MarginLayoutParams.verticalMargin: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(v) {
        topMargin = v
        bottomMargin = v
    }

var ViewGroup.MarginLayoutParams.horizontalMargin: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(v) {
        leftMargin = v; rightMargin = v
    }

var ViewGroup.MarginLayoutParams.margin: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(v) {
        leftMargin = v
        rightMargin = v
        topMargin = v
        bottomMargin = v
    }


inline val Context.vibrator: android.os.Vibrator
    get() = getSystemService(Context.VIBRATOR_SERVICE) as android.os.Vibrator

inline val Context.layoutInflater: android.view.LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

@PublishedApi
internal object `$$Anko$Factories$CustomViews` {
    val VERTICAL_LAYOUT_FACTORY = { ctx: Context ->
        val view = _LinearLayout(ctx)
        view.orientation = LinearLayout.VERTICAL
        view
    }

    val EDIT_TEXT = { ctx: Context -> EditText(ctx) }

    val HORIZONTAL_PROGRESS_BAR_FACTORY = { ctx: Context ->
        ProgressBar(ctx, null, android.R.attr.progressBarStyleHorizontal)
    }
}

inline fun ViewManager.verticalLayout(theme: Int = 0): LinearLayout = verticalLayout(theme) {}
inline fun ViewManager.verticalLayout(
    theme: Int = 0,
    init: (@AnkoViewDslMarker _LinearLayout).() -> Unit
): LinearLayout {
    return ankoView(`$$Anko$Factories$CustomViews`.VERTICAL_LAYOUT_FACTORY, theme, init)
}

inline fun Context.verticalLayout(theme: Int = 0): LinearLayout = verticalLayout(theme) {}
inline fun Context.verticalLayout(theme: Int = 0, init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$CustomViews`.VERTICAL_LAYOUT_FACTORY, theme, init)
}

inline fun Activity.verticalLayout(theme: Int = 0): LinearLayout = verticalLayout(theme) {}
inline fun Activity.verticalLayout(theme: Int = 0, init: (@AnkoViewDslMarker _LinearLayout).() -> Unit): LinearLayout {
    return ankoView(`$$Anko$Factories$CustomViews`.VERTICAL_LAYOUT_FACTORY, theme, init)
}

inline fun ViewManager.editText(constraints: InputConstraints, theme: Int = 0): EditText =
    editText(constraints, theme) {}

inline fun ViewManager.editText(
    constraints: InputConstraints,
    theme: Int = 0,
    init: (@AnkoViewDslMarker EditText).() -> Unit
): EditText {
    val v = ankoView(`$$Anko$Factories$CustomViews`.EDIT_TEXT, theme, init)
    v.inputType = constraints.value
    return v
}

inline fun Context.editText(constraints: InputConstraints, theme: Int = 0): EditText = editText(constraints, theme) {}
inline fun Context.editText(
    constraints: InputConstraints,
    theme: Int = 0,
    init: (@AnkoViewDslMarker EditText).() -> Unit
): EditText {
    val v = ankoView(`$$Anko$Factories$CustomViews`.EDIT_TEXT, theme, init)
    v.inputType = constraints.value
    return v
}

inline fun Activity.editText(constraints: InputConstraints, theme: Int = 0): EditText = editText(constraints, theme) {}
inline fun Activity.editText(
    constraints: InputConstraints,
    theme: Int = 0,
    init: (@AnkoViewDslMarker EditText).() -> Unit
): EditText {
    val v = ankoView(`$$Anko$Factories$CustomViews`.EDIT_TEXT, theme, init)
    v.inputType = constraints.value
    return v
}

inline fun ViewManager.horizontalProgressBar(theme: Int = 0): ProgressBar = horizontalProgressBar(theme) {}
inline fun ViewManager.horizontalProgressBar(
    theme: Int = 0,
    init: (@AnkoViewDslMarker ProgressBar).() -> Unit
): ProgressBar {
    return ankoView(`$$Anko$Factories$CustomViews`.HORIZONTAL_PROGRESS_BAR_FACTORY, theme, init)
}

inline fun Context.horizontalProgressBar(theme: Int = 0): ProgressBar = horizontalProgressBar(theme) {}
inline fun Context.horizontalProgressBar(
    theme: Int = 0,
    init: (@AnkoViewDslMarker ProgressBar).() -> Unit
): ProgressBar {
    return ankoView(`$$Anko$Factories$CustomViews`.HORIZONTAL_PROGRESS_BAR_FACTORY, theme, init)
}

inline fun Activity.horizontalProgressBar(theme: Int = 0): ProgressBar = horizontalProgressBar(theme) {}
inline fun Activity.horizontalProgressBar(
    theme: Int = 0,
    init: (@AnkoViewDslMarker ProgressBar).() -> Unit
): ProgressBar {
    return ankoView(`$$Anko$Factories$CustomViews`.HORIZONTAL_PROGRESS_BAR_FACTORY, theme, init)
}

inline fun <T : View> ViewManager.include(layoutId: Int): T = include(layoutId, {})
inline fun <T : View> ViewManager.include(layoutId: Int, init: (@AnkoViewDslMarker T).() -> Unit): T {
    @Suppress("UNCHECKED_CAST")
    return ankoView({ ctx -> ctx.layoutInflater.inflate(layoutId, null) as T }, 0) { init() }
}

inline fun <T : View> ViewGroup.include(layoutId: Int): T = include(layoutId, {})
inline fun <T : View> ViewGroup.include(layoutId: Int, init: (@AnkoViewDslMarker T).() -> Unit): T {
    @Suppress("UNCHECKED_CAST")
    return ankoView({ ctx -> ctx.layoutInflater.inflate(layoutId, this, false) as T }, 0) { init() }
}

inline fun <T : View> Context.include(layoutId: Int): T = include(layoutId, {})
inline fun <T : View> Context.include(layoutId: Int, init: (@AnkoViewDslMarker T).() -> Unit): T {
    @Suppress("UNCHECKED_CAST")
    return ankoView({ ctx -> ctx.layoutInflater.inflate(layoutId, null) as T }, 0) { init() }
}

inline fun <T : View> Activity.include(layoutId: Int): T = include(layoutId, {})
inline fun <T : View> Activity.include(layoutId: Int, init: (@AnkoViewDslMarker T).() -> Unit): T {
    @Suppress("UNCHECKED_CAST")
    return ankoView({ ctx -> ctx.layoutInflater.inflate(layoutId, null) as T }, 0) { init() }
}


var View.backgroundDrawable: Drawable?
    inline get() = background
    set(value) {
        background = value
    }

var View.backgroundColorResource: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(colorId) = setBackgroundColor(context.resources.getColor(colorId))

var View.leftPadding: Int
    inline get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.rightPadding: Int
    inline get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

var View.bottomPadding: Int
    inline get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

@Deprecated("Use horizontalPadding instead", ReplaceWith("horizontalPadding"))
var View.paddingHorizontal: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(value) = setPadding(value, paddingTop, value, paddingBottom)

var View.horizontalPadding: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(value) = setPadding(value, paddingTop, value, paddingBottom)

@Deprecated("Use verticalPadding instead", ReplaceWith("verticalPadding"))
var View.paddingVertical: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(value) = setPadding(paddingLeft, value, paddingRight, value)

var View.verticalPadding: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(value) = setPadding(paddingLeft, value, paddingRight, value)

var View.padding: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    inline set(value) = setPadding(value, value, value, value)

var TextView.allCaps: Boolean
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    inline set(value) {
        isAllCaps = value
    }

var TextView.ems: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    inline set(value) = setEms(value)

inline var TextView.isSelectable: Boolean
    get() = isTextSelectable
    set(value) = setTextIsSelectable(value)

var TextView.textAppearance: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(value) = if (Build.VERSION.SDK_INT >= 23) setTextAppearance(value) else setTextAppearance(context, value)

var TextView.textSizeDimen: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(value) = setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(value))

var TextView.textColorResource: Int
    @Deprecated(NO_GETTER, level = ERROR) get() = noGetter()
    set(colorId) = setTextColor(context.resources.getColor(colorId))

var ImageView.image: Drawable?
    inline get() = drawable
    inline set(value) = setImageDrawable(value)

enum class InputConstraints(val value: Int) {
    PASSWORD(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
}

/**
 * Create a plain menu item
 */
inline fun Menu.item(title: CharSequence, /*@DrawableRes*/ icon: Int = 0, checkable: Boolean = false): MenuItem =
    add(title).apply {
        setIcon(icon)
        isCheckable = checkable
    }

/**
 * Create a menu item and configure it
 */
inline fun Menu.item(
    title: CharSequence, /*@DrawableRes*/
    icon: Int = 0,
    checkable: Boolean = false,
    configure: MenuItem.() -> Unit
): MenuItem =
    add(title).apply {
        setIcon(icon)
        isCheckable = checkable
        configure()
    }

/**
 * Create a menu item with title from resources
 */
inline fun Menu.item(/*@StringRes*/ titleRes: Int, /*@DrawableRes*/
                                    icon: Int = 0,
                                    checkable: Boolean = false
): MenuItem =
    add(titleRes).apply {
        setIcon(icon)
        isCheckable = checkable
    }

/**
 * Create a menu item with title from resources and configure it
 */
inline fun Menu.item(/*@StringRes*/ titleRes: Int, /*@DrawableRes*/
                                    icon: Int = 0,
                                    checkable: Boolean = false,
                                    configure: MenuItem.() -> Unit
): MenuItem =
    add(titleRes).apply {
        setIcon(icon)
        isCheckable = checkable
        configure()
    }


/**
 * Create a submenu
 */
inline fun Menu.subMenu(title: CharSequence): SubMenu =
    addSubMenu(title)

/**
 * Create a submenu and configure it
 */
inline fun Menu.subMenu(title: CharSequence, configure: SubMenu.() -> Unit): SubMenu =
    addSubMenu(title).apply { configure() }

/**
 * Create a submenu with title from resources
 */
inline fun Menu.subMenu(/*@StringRes*/ titleRes: Int): SubMenu =
    addSubMenu(titleRes)

/**
 * Create a submenu with title from resources and configure it
 */
inline fun Menu.subMenu(/*@StringRes*/ titleRes: Int, configure: SubMenu.() -> Unit): SubMenu =
    addSubMenu(titleRes).apply { configure() }


/**
 * Create a checkable menu item for use in NavigationView
 */
inline fun Menu.navigationItem(title: CharSequence, /*@DrawableRes*/ icon: Int = 0): Unit {
    add(title).apply {
        setIcon(icon)
        isCheckable = true
    }
}

/**
 * Create a navigation item with OnClickListener
 */
inline fun Menu.navigationItem(title: CharSequence, /*@DrawableRes*/ icon: Int = 0, crossinline onClick: () -> Unit) {
    add(title).apply {
        setIcon(icon)
        isCheckable = true
        setOnMenuItemClickListener {
            onClick()
            false
        }
    }
}

/**
 * Create a navigation item with title from resources
 */
inline fun Menu.navigationItem(/*@StringRes*/ titleRes: Int, /*@DrawableRes*/ icon: Int = 0) {
    add(titleRes).apply {
        setIcon(icon)
        isCheckable = true
    }
}

/**
 * Create a navigation item with title from resources and onClick listener
 */
inline fun Menu.navigationItem(/*@StringRes*/ titleRes: Int, /*@DrawableRes*/
                                              icon: Int = 0,
                                              crossinline onClick: () -> Unit
) {
    add(titleRes).apply {
        setIcon(icon)
        isCheckable = true
        setOnMenuItemClickListener {
            onClick()
            false
        }
    }
}


/**
 * Set OnClickListener on a menu item
 */
inline fun MenuItem.onClick(consume: Boolean = true, crossinline action: () -> Unit): Unit {
    setOnMenuItemClickListener { action(); consume }
}