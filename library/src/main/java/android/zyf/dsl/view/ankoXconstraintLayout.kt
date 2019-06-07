@file:Suppress("UNUSED_PARAMETER")

package android.zyf.dsl.view

import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import android.view.View
import android.zyf.dsl.view.ConstraintSetBuilder.Side
import android.zyf.dsl.view.ConstraintSetBuilder.Connection.BasicConnection
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.children
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.internals.AnkoInternals.noGetter
import java.lang.ref.WeakReference

sealed class Border {
    object Parent : Border()
}

val ConstraintLayout.matchConstraint
    get() = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT

fun ConstraintLayout.generateViewId() {
    this.children.forEach {
        if (it.id == -1) {
            it.id = View.generateViewId()
        }
    }
}

fun ConstraintLayout.applyConstraintSet(init: ConstraintSetBuilder.() -> Unit): ConstraintSet =
    constraintSet(init).also { it.applyTo(this) }

fun ConstraintLayout.constraintSet(init: ConstraintSetBuilder.() -> Unit): ConstraintSet =
    ConstraintSetBuilder().also { it.clone(this) }.apply(init)

open class LinkGroup(vararg views: View) {
    var orientation = LayoutParams.HORIZONTAL
    var chainType = ConstraintSet.CHAIN_PACKED
    internal val list = views.toMutableList()
    internal var startBorder: Border? = null
    internal var endBorder: Border? = null
    infix operator fun View.rangeTo(view: View): LinkGroup {
        list.add(this)
        list.add(view)
        return this@LinkGroup
    }

    infix operator fun LinkGroup.rangeTo(view: View): LinkGroup {
        list.add(view)
        return this@LinkGroup
    }

    infix operator fun LinkGroup.rangeTo(end: Border) {
        endBorder = end
    }

    infix operator fun Border.rangeTo(view: View): LinkGroup {
        startBorder = this
        list.add(0, view)
        return this@LinkGroup
    }

    infix fun Border.rangeTo(group: LinkGroup): LinkGroup {
        startBorder = this
        return this@LinkGroup
    }
}

open class ViewConstraintBuilder(
    @IdRes private val viewId: Int,
    private val constraintSetBuilder: ConstraintSetBuilder
) {
    infix fun Pair<Side, Side>.of(@IdRes targetViewId: Int): BasicConnection =
        constraintSetBuilder.run { (first of viewId) to (second of targetViewId) }

    infix fun Pair<Side, Side>.of(targetView: View): BasicConnection = this of targetView.id

    fun clear() {
        constraintSetBuilder.clear(viewId)
    }

    fun clear(sideId: Int) {
        constraintSetBuilder.clear(viewId, sideId)
    }

    fun setMargin(sideId: Int, value: Int) {
        constraintSetBuilder.setMargin(viewId, sideId, value)
    }
    /*
    * 4 value : start end top bottom start end
    * 2 value : (start end) (top bottom)
    * 1 value: all
    * */

    fun setGoneMargin(sideId: Int, value: Int) {
        constraintSetBuilder.setGoneMargin(viewId, sideId, value)
    }

    var horizontalBias: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setHorizontalBias(viewId, value)
        }

    var verticalBias: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setVerticalBias(viewId, value)
        }

    var dimensionRatio: String
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setDimensionRatio(viewId, value)
        }

    var visibility: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setVisibility(viewId, value)
        }

    var alpha: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setAlpha(viewId, value)
        }

    var applyElevation: Boolean
        get() = constraintSetBuilder.getApplyElevation(viewId)
        set(value) {
            constraintSetBuilder.setApplyElevation(viewId, value)
        }

    var elevation: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setElevation(viewId, value)
        }

    var rotationX: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setRotationX(viewId, value)
        }

    var rotationY: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setRotationY(viewId, value)
        }

    var scaleX: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setScaleX(viewId, value)
        }

    var scaleY: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setScaleY(viewId, value)
        }

    var transformPivotX: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setTransformPivotX(viewId, value)
        }

    var transformPivotY: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setTransformPivotY(viewId, value)
        }

    var translationX: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setTranslationX(viewId, value)
        }

    var translationY: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setTranslationY(viewId, value)
        }

    var translationZ: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setTranslationZ(viewId, value)
        }

    var height: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainHeight(viewId, value)
        }

    var width: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainWidth(viewId, value)
        }
    var percentHeight: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainPercentHeight(viewId, value)
        }

    var percentWidth: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainPercentWidth(viewId, value)
        }

    var maxHeight: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainMaxHeight(viewId, value)
        }

    var maxWidth: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainMaxWidth(viewId, value)
        }

    var minHeight: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainMinHeight(viewId, value)
        }

    var minWidth: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainMinWidth(viewId, value)
        }

    var defaultHeight: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainDefaultHeight(viewId, value)
        }

    var defaultWidth: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.constrainDefaultWidth(viewId, value)
        }
    var horizontalWeight: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setHorizontalWeight(viewId, value)
        }

    var verticalWeight: Float
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setVerticalWeight(viewId, value)
        }

    var horizontalChainStyle: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setHorizontalChainStyle(viewId, value)
        }

    var verticalChainStyle: Int
        @Deprecated(AnkoInternals.NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            constraintSetBuilder.setVerticalChainStyle(viewId, value)
        }
}

open class ConstraintSetBuilder : ConstraintSet() {
    infix fun View.verticalBias(value: Float) {
        setVerticalBias(this.id, value)
    }

    infix fun View.horizontalBias(value: Float) {
        setHorizontalBias(this.id, value)
    }

    infix fun List<View>.verticalBias(value: Float) {
        forEach {
            setVerticalBias(it.id, value)
        }
    }

    infix fun List<View>.horizontalBias(value: Float) {
        forEach {
            setHorizontalBias(it.id, value)
        }
    }

    infix fun View.horizontalChainStyle(chainStyle: Int) {
        setHorizontalChainStyle(this.id, ConstraintSet.CHAIN_PACKED)
    }

    infix fun View.verticalChainStyle(chainStyle: Int) {
        setVerticalChainStyle(this.id, ConstraintSet.CHAIN_PACKED)
    }
    operator fun Int.invoke(init: ViewConstraintBuilder.() -> Unit) {
        ViewConstraintBuilder(this, this@ConstraintSetBuilder).apply(init)
    }

    infix fun List<View>.margin(values: Array<Int>) {
        this.forEach {
            it margin values
        }
    }

    infix fun View.margin(values: Array<Int>) {
        when (values.size) {
            1 -> {
                setMargin(this.id, ConstraintSet.START, values[0])
                setMargin(this.id, ConstraintSet.END, values[0])
                setMargin(this.id, ConstraintSet.TOP, values[0])
                setMargin(this.id, ConstraintSet.BOTTOM, values[0])
            }
            2 -> {
                setMargin(this.id, ConstraintSet.START, values[0])
                setMargin(this.id, ConstraintSet.END, values[0])
                setMargin(this.id, ConstraintSet.TOP, values[1])
                setMargin(this.id, ConstraintSet.BOTTOM, values[1])
            }
            4 -> {
                setMargin(this.id, ConstraintSet.START, values[0])
                setMargin(this.id, ConstraintSet.END, values[1])
                setMargin(this.id, ConstraintSet.TOP, values[2])
                setMargin(this.id, ConstraintSet.BOTTOM, values[3])
            }
        }
    }

    fun View.top2parent() = connect(this.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

    infix fun View.top2(border: Border) {
        when (border) {
            is Border.Parent -> {
                connect(this.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            }
        }
    }

    fun View.bottom2parent() = connect(this.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

    infix fun View.bottom2(border: Border) {
        when (border) {
            is Border.Parent -> {
                connect(this.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }
        }
    }

    fun View.start2parent() = connect(this.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

    infix fun View.start2(border: Border) {
        when (border) {
            is Border.Parent -> {
                connect(this.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            }
        }
    }

    fun View.end2parent() = connect(this.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

    infix fun View.end2(border: Border) {
        when (border) {
            is Border.Parent -> {
                connect(this.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            }
        }
    }

    infix fun List<View>.top2(border: Border) {
        when (border) {
            is Border.Parent -> {
                this.forEach {
                    connect(it.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                }
            }
        }
    }

    infix fun List<View>.bottom2(border: Border) {
        when (border) {
            is Border.Parent -> {
                this.forEach {
                    connect(it.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                }
            }
        }
    }

    infix fun List<View>.start2(border: Border) {
        when (border) {
            is Border.Parent -> {
                this.forEach {
                    connect(it.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                }
            }
        }
    }

    infix fun List<View>.end2(border: Border) {
        when (border) {
            is Border.Parent -> {
                this.forEach {
                    connect(it.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                }
            }
        }
    }

    infix fun View.top2top(targetView: View) {
        connect(this.id, ConstraintSet.TOP, targetView.id, ConstraintSet.TOP)
    }

    infix fun View.top2bottom(targetView: View) {
        connect(this.id, ConstraintSet.TOP, targetView.id, ConstraintSet.BOTTOM)
    }

    infix fun View.bottom2top(targetView: View) {
        connect(this.id, ConstraintSet.BOTTOM, targetView.id, ConstraintSet.TOP)
    }

    infix fun View.bottom2bottom(targetView: View) {
        connect(this.id, ConstraintSet.BOTTOM, targetView.id, ConstraintSet.BOTTOM)
    }

    infix fun View.start2start(targetView: View) {
        connect(this.id, ConstraintSet.START, targetView.id, ConstraintSet.START)
    }

    infix fun View.start2end(targetView: View) {
        connect(this.id, ConstraintSet.START, targetView.id, ConstraintSet.END)
    }

    infix fun View.end2end(targetView: View) {
        connect(this.id, ConstraintSet.END, targetView.id, ConstraintSet.END)
    }

    infix fun View.end2start(targetView: View) {
        connect(this.id, ConstraintSet.END, targetView.id, ConstraintSet.START)
    }

    infix fun List<View>.top2top(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.TOP, targetView.id, ConstraintSet.TOP)
        }
    }

    infix fun List<View>.top2bottom(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.TOP, targetView.id, ConstraintSet.BOTTOM)
        }
    }

    infix fun List<View>.bottom2top(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.BOTTOM, targetView.id, ConstraintSet.TOP)
        }
    }

    infix fun List<View>.bottom2bottom(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.BOTTOM, targetView.id, ConstraintSet.BOTTOM)
        }
    }

    infix fun List<View>.start2start(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.START, targetView.id, ConstraintSet.START)
        }
    }

    infix fun List<View>.start2end(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.START, targetView.id, ConstraintSet.END)
        }
    }

    infix fun List<View>.end2end(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.END, targetView.id, ConstraintSet.END)
        }
    }

    infix fun List<View>.end2start(targetView: View) {
        this.forEach {
            connect(it.id, ConstraintSet.END, targetView.id, ConstraintSet.START)
        }
    }

    fun List<View>.all2Parent() {
        this.forEach {
            it.start2parent()
            it.end2parent()
            it.top2parent()
            it.bottom2parent()
        }
    }

    infix fun View.visibility(v: Int) {
        setVisibility(this.id, v)
    }

    infix fun List<View>.visibility(v: Int) {
        this.forEach {
            setVisibility(it.id, v)
        }
    }

    fun chain(init: LinkGroup.() -> Unit) {
        val group = LinkGroup()
        group.init()
        val viewList = group.list
        if (viewList.size > 1) {
            when (group.orientation) {
                LayoutParams.HORIZONTAL -> {
                    for (index in 0 until viewList.size - 1) {
                        val leftView = viewList[index]
                        val rightView = viewList[index + 1]
                        leftView end2start rightView
                        rightView start2end leftView
                    }
                    group.startBorder?.also { start ->
                        when (start) {
                            is Border.Parent -> {
                                viewList[0].start2parent()
                            }
                        }
                    }
                    group.endBorder?.also { end ->
                        when (end) {
                            is Border.Parent -> {
                                viewList[viewList.size - 1].end2parent()
                            }
                        }
                    }
                    this@ConstraintSetBuilder.setHorizontalChainStyle(viewList[0].id, group.chainType)
                }
                LayoutParams.VERTICAL -> {
                    for (index in 0 until viewList.size - 1) {
                        val topView = viewList[index]
                        val bottomView = viewList[index + 1]
                        topView bottom2top bottomView
                        bottomView top2bottom topView
                    }
                    group.startBorder?.also { start ->
                        when (start) {
                            is Border.Parent -> {
                                viewList[0].top2parent()
                            }
                        }
                    }
                    group.endBorder?.also { end ->
                        when (end) {
                            is Border.Parent -> {
                                viewList[viewList.size - 1].bottom2parent()
                            }
                        }
                    }
                    this@ConstraintSetBuilder.setVerticalChainStyle(viewList[0].id, group.chainType)
                }
            }
        }
    }

    operator fun View.invoke(init: ViewConstraintBuilder.() -> Unit) = id.invoke(init)

    operator fun List<View>.invoke(init: ViewConstraintBuilder.() -> Unit) {
        forEach {
            it.id.invoke(init)
        }
    }

    /*
    * widthPercent
    * heightPercent
    * ratio: width : height
    * */
    fun View.percent(widthPercent: Float? = null, heightPercent: Float? = null, ratio: Pair<Int, Int>? = null) {
        if (widthPercent != null && heightPercent != null) {
            this@ConstraintSetBuilder.constrainPercentWidth(this.id, widthPercent)
            this@ConstraintSetBuilder.constrainPercentHeight(this.id, heightPercent)
            this@ConstraintSetBuilder.constrainDefaultHeight(id, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_PERCENT)
            this@ConstraintSetBuilder.constrainDefaultWidth(id, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_PERCENT)
        } else if (heightPercent != null) {
            ratio!!
            val dimensionRatio = "w,${ratio.first}:${ratio.second}"
            this@ConstraintSetBuilder.constrainDefaultHeight(id, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_PERCENT)
            this@ConstraintSetBuilder.constrainPercentHeight(id, heightPercent)
            this@ConstraintSetBuilder.constrainDefaultWidth(id, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD)
            this@ConstraintSetBuilder.constrainPercentWidth(id, 1f)
            this@ConstraintSetBuilder.setDimensionRatio(id, dimensionRatio)
        } else if (widthPercent != null) {
            ratio!!
            val dimensionRatio = "h,${ratio.first}:${ratio.second}"
            this@ConstraintSetBuilder.constrainDefaultHeight(id, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD)
            this@ConstraintSetBuilder.constrainPercentHeight(id, 1f)
            this@ConstraintSetBuilder.constrainDefaultWidth(id, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_PERCENT)
            this@ConstraintSetBuilder.constrainPercentWidth(id, widthPercent)
            this@ConstraintSetBuilder.setDimensionRatio(id, dimensionRatio)
        }

    }

    fun List<View>.percent(widthPercent: Float? = null, heightPercent: Float? = null, ratio: Pair<Int, Int>? = null) {
        forEach {
            it.percent(widthPercent, heightPercent, ratio)
        }
    }

    infix fun Side.of(@IdRes viewId: Int) = when (this) {
        Side.LEFT -> ViewSide.Left(viewId)
        Side.RIGHT -> ViewSide.Right(viewId)
        Side.TOP -> ViewSide.Top(viewId)
        Side.BOTTOM -> ViewSide.Bottom(viewId)
        Side.BASELINE -> ViewSide.Baseline(viewId)
        Side.START -> ViewSide.Start(viewId)
        Side.END -> ViewSide.End(viewId)
    }

    infix fun Side.of(view: View) = this of view.id

    infix fun Pair<ViewSide, Side>.of(@IdRes viewId: Int) = first to (second of viewId)

    infix fun Pair<ViewSide, Side>.of(view: View) = first to (second of view.id)

    infix fun ViewSide.to(targetSide: ViewSide) = BasicConnection(this, targetSide)

    infix fun BasicConnection.margin(margin: Int) = Connection.MarginConnection(from, to, margin)

    fun connect(vararg connections: Connection) {
        for (connection in connections) {
            when (connection) {
                is Connection.MarginConnection -> connect(
                    connection.from.viewId,
                    connection.from.sideId,
                    connection.to.viewId,
                    connection.to.sideId,
                    connection.margin
                )
                is BasicConnection -> connect(
                    connection.from.viewId,
                    connection.from.sideId,
                    connection.to.viewId,
                    connection.to.sideId
                )
            }
        }
    }

    enum class Side {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        BASELINE,
        START,
        END,
    }

    sealed class ViewSide(@IdRes val viewId: Int) {
        class Left(@IdRes viewId: Int) : ViewSide(viewId)
        class Right(@IdRes viewId: Int) : ViewSide(viewId)
        class Top(@IdRes viewId: Int) : ViewSide(viewId)
        class Bottom(@IdRes viewId: Int) : ViewSide(viewId)
        class Baseline(@IdRes viewId: Int) : ViewSide(viewId)
        class Start(@IdRes viewId: Int) : ViewSide(viewId)
        class End(@IdRes viewId: Int) : ViewSide(viewId)

        val sideId: Int
            get() = when (this) {
                is ViewSide.Left -> ConstraintSet.LEFT
                is ViewSide.Right -> ConstraintSet.RIGHT
                is ViewSide.Top -> ConstraintSet.TOP
                is ViewSide.Bottom -> ConstraintSet.BOTTOM
                is ViewSide.Baseline -> ConstraintSet.BASELINE
                is ViewSide.Start -> ConstraintSet.START
                is ViewSide.End -> ConstraintSet.END
            }
    }

    sealed class Connection(val from: ViewSide, val to: ViewSide) {
        class BasicConnection(from: ViewSide, to: ViewSide) : Connection(from, to)
        class MarginConnection(from: ViewSide, to: ViewSide, val margin: Int) : Connection(from, to)
    }
}