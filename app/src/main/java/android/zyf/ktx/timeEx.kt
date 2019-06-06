package android.zyf.ktx

import timber.log.Timber

inline infix fun <R> String.showTakeTime(block: () -> R): R {
    Timber.e("$this start cal the time")
    val start = System.currentTimeMillis()
    val result = block()
    val end = System.currentTimeMillis()
    Timber.e("$this take up time: ${end - start}")
    return result
}

