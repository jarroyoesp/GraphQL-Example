package com.jarroyo.graphqlexample.presentation.utils

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

private const val TRANSPARENT_ALPHA_VALUE = 0

private const val OPAQUE_ALPHA_VALUE = 255

fun View.setSize(@DimenRes size: Int) {
    context?.resources?.let {
        val dimenSize = it.getDimension(size).toInt()

        layoutParams.run {
            width = dimenSize
            height = dimenSize
        }
    }
}

fun ImageView.resetAnimation() {
    clearAnimation()
    setBackgroundResource(0)
}

fun ImageView.setTransparentAlpha() {
    imageAlpha = TRANSPARENT_ALPHA_VALUE
}

fun ImageView.setOpaqueAlpha() {
    imageAlpha = OPAQUE_ALPHA_VALUE
}

fun ImageView.isImageAnimationRunning(): Boolean =
    (this.background as? AnimationDrawable)?.isRunning ?: false

fun ImageView.startImageAnimation(animation: Int) {
    this.setBackgroundResource(animation)
    (this.background as? AnimationDrawable)?.start()
}

fun ViewGroup.inflateWithoutRoot(@LayoutRes layoutRes: Int): View = LayoutInflater.from(context)
    .inflate(layoutRes, null, false)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater.from(this.context)
    .inflate(layoutRes, this, false)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attached: Boolean = false): View = this.inflate(
    LayoutInflater.from(context), layoutRes, attached
)

fun ViewGroup.inflate(
    inflater: LayoutInflater, @LayoutRes layoutRes: Int, attached: Boolean = false
): View = inflater.inflate(layoutRes, this, attached)

fun <T : View> T?.visible(): T? = this?.apply { visibility = View.VISIBLE }

fun <T : View> T?.invisible(): T? = this?.apply { visibility = View.INVISIBLE }

fun <T : View> T?.gone(): T? = this?.apply { visibility = View.GONE }

fun View.isVisible() = this.visibility == View.VISIBLE
fun View.isGone() = this.visibility == View.GONE

fun View.setBottomMargin(bottomPadding: Int, layoutParams: ViewGroup.MarginLayoutParams?) {
    this.layoutParams = layoutParams?.apply {
        setMargins(leftMargin, topMargin, rightMargin, bottomPadding)
    } ?: run {
        (this.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            setMargins(leftMargin, topMargin, rightMargin, bottomPadding)
        }
    }
}

fun View.setTopMargin(topPadding: Int, layoutParams: ViewGroup.MarginLayoutParams?) {
    this.layoutParams = layoutParams?.apply {
        setMargins(leftMargin, topPadding, rightMargin, bottomMargin)
    } ?: run {
        (this.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            setMargins(leftMargin, topPadding, rightMargin, bottomMargin)
        }
    }
}

fun View.setBottomPadding(bottomPadding: Int) =
    setPadding(paddingLeft, paddingTop, paddingRight, bottomPadding)


fun EditText.onKeySearchClickListener(onClickDone: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onClickDone.invoke()
        }
        false
    }
}

fun RecyclerView.centerSnap() {
    val snapHelper = object : LinearSnapHelper() {
        override fun findTargetSnapPosition(
            layoutManager: RecyclerView.LayoutManager?, x: Int, y: Int
        ): Int {

            var targetPosition = -1
            layoutManager?.apply {
                val centerView = findSnapView(this) ?: return RecyclerView.NO_POSITION

                getPosition(centerView).apply {
                    when {
                        canScrollHorizontally() -> {
                            targetPosition = if (x < 0) {
                                this - 1
                            } else {
                                this + 1
                            }
                        }
                        canScrollVertically() -> {
                            targetPosition = if (y < 0) {
                                this - 1
                            } else {
                                this + 1
                            }
                        }
                        else -> {
                        }
                    }
                }

                val firstItem = 0
                val lastItem = itemCount - 1
                targetPosition = min(lastItem, max(targetPosition, firstItem))
            }
            return targetPosition
        }
    }
    snapHelper.attachToRecyclerView(this)
}

fun <T> RecyclerView.Adapter<*>.notifyChanges(
    oldList: List<T>,
    newList: List<T>,
    compare: (T, T) -> Boolean
) {

    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            compare(oldList[oldItemPosition], newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    })
    diff.dispatchUpdatesTo(this)
}

