@file:Suppress("UNUSED_PARAMETER")

package android.zyf.ktx

import android.app.Application
import android.util.Log
import timber.log.Timber
import timber.log.Timber.DebugTree


fun Application.buildSimplyTimber(isDebug: Boolean) {
    if (isDebug) {
        Timber.plant(DebugTree())
    } else {
        //Timber.plant(CrashReportingTree())
    }
}

/** A tree which logs important information for crash reporting.  */
private class CrashReportingTree : Timber.Tree() {
    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See [Log] for constants.
     * @param tag Explicit or inferred tag. May be `null`.
     * @param message Formatted log message. May be `null`, but then `t` will not be.
     * @param t Accompanying exceptions. May be `null`, but then `message` will not be.
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
        FakeCrashLibrary.log(priority, tag, message)
        if (t != null) {
            if (priority == Log.ERROR) {
                FakeCrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                FakeCrashLibrary.logWarning(t)
            }
        }
    }

    fun isLoggable(priority: Int, tag: String): Boolean {
        return priority >= Log.INFO
    }
}

@Suppress("UseExpressionBody")
class FakeCrashLibrary private constructor() {
    companion object {
        fun log(priority: Int, tag: String?, message: String) {
            // TODO add log entry to circular buffer.
        }

        fun logWarning(t: Throwable) {
            // TODO report non-fatal warning.
        }

        fun logError(t: Throwable) {
            // TODO report non-fatal error.
        }
    }
}
