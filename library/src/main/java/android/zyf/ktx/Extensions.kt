@file:Suppress("NOTHING_TO_INLINE", "UNUSED_ANONYMOUS_PARAMETER")

package android.zyf.ktx

import android.annotation.SuppressLint
import android.app.*
import android.app.Notification
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothProfile
import android.content.*
import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import android.content.pm.ApplicationInfo
import androidx.databinding.ObservableField
import android.os.Looper
import androidx.annotation.DimenRes
import androidx.core.app.NotificationCompat
import android.util.TypedValue
import android.widget.TextView
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

fun isMainThread(): Boolean {
    return Looper.getMainLooper() == Looper.myLooper()
}

fun <R> runOnMain(block: () -> R): R {
    return if (isMainThread()) {
        block.invoke()
    } else {
        runBlocking {
            withContext(Dispatchers.Main) {
                block.invoke()
            }
        }
    }
}

inline fun <reified T : ViewModel> ViewModelProvider.get(): T {
    return this.get(T::class.java)
}


inline fun Service.registerVolumeChange(crossinline block: (Int) -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action ?: return
            if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                block(currVolume)
            }
        }
    }.apply {
        val intent = IntentFilter().apply { addAction("android.media.VOLUME_CHANGED_ACTION") }
        this@registerVolumeChange.registerReceiver(this, intent)
    }
}

inline fun Service.registerBluetoothChange(crossinline connectionStateChanged: (Intent, Int) -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action ?: return
            if (action == BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED) {
                connectionStateChanged(intent, intent.getIntExtra(BluetoothProfile.EXTRA_STATE, -1))
            }
        }
    }.apply {
        val intent = IntentFilter().apply { addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED) }
        this@registerBluetoothChange.registerReceiver(this, intent)
    }
}

inline fun ContextWrapper.registerWifiStateChanged(crossinline callback: (Intent) -> Unit): BroadcastReceiver {
    val action = "android.net.wifi.WIFI_STATE_CHANGED"
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action!!.equals(action)) {
                callback(intent)
            }
        }
    }.apply {
        val intent = IntentFilter().apply { addAction(action) }
        this@registerWifiStateChanged.registerReceiver(this, intent)
    }
}


/*inline fun <T> Array<out T>.firstIndexed(predicate: (index: Int,T) -> Boolean): T {
    for ((index,element) in this.withIndex()) {
        if (predicate(index,element)){
            return element
        }
    }
    throw NoSuchElementException("Array contains no element matching the predicate.")
}*/

inline fun <T> Array<T>.firstIndexed(
    filter: (index: Int, T) -> Boolean,
    onNext: (index: Int, T) -> Unit = { i, t -> },
    onSuccess: () -> Unit = {},
    onFail: () -> Unit = {},
    onComplete: () -> Unit = {}
) {
    var isNull = true
    for ((index, element) in this.withIndex()) {
        if (filter(index, element)) {
            isNull = false
            onNext(index, element)
            break
        }
    }
    if (isNull) onFail() else onSuccess()
    onComplete()
}

inline fun <reified T> initArrayOf(size: Int, predicate: (index: Int) -> T): Array<T> {
    return arrayListOf<T>().also {
        for (index in 0 until size) {
            it.add(predicate(index))
        }
    }.toTypedArray()
}


inline fun View.toast(message: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this.context, message, duration).show()

inline fun androidx.fragment.app.Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this.activity, message, duration).show()

inline fun Activity.toast(message: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, message, duration).show()

inline fun View.toast(@StringRes rid: Int, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this.context, rid, duration).show()

inline fun androidx.fragment.app.Fragment.toast(@StringRes rid: Int, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this.activity, rid, duration).show()

inline fun Activity.toast(@StringRes rid: Int, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, rid, duration).show()

@SuppressLint("CheckResult")
inline fun Service.safeToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(this@safeToast, message, duration).show()
    }
}

inline fun <reified T : Service> Context.isServiceRunning(): Boolean {
    val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val serviceList = activityManager.getRunningServices(Integer.MAX_VALUE)
    if (serviceList.size <= 0) {
        return false
    }
    serviceList.firstOrNull {
        Timber.w("${it.service.className} == ${T::class.java.name} ")
        it.service.className == T::class.java.name
    }?.apply {
        return true
    }
    return false
}

inline fun <reified T : Service> Context.startForegroundService(predicate: Context.(Intent) -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    predicate.invoke(this, intent)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}

inline fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

inline fun Context.musicVolume(): Int = (this.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
    .getStreamVolume(AudioManager.STREAM_MUSIC)

inline fun Context.propertyInAssets(propertyName: String) = Properties().also {
    val inputStream = this.assets.open("${propertyName}.properties")
    it.load(inputStream)
    inputStream.close()
}

inline fun Context.jsonInAssets(jsonName: String): String {
    StringBuilder().let {
        this.assets.open(jsonName)
            .bufferedReader()
            .forEachLine { line ->
                it.append(line)
            }
        return it.toString()
    }
}

inline fun Context.xmlInAssets(xmlName: String) = this.assets.open(xmlName)

inline fun Context.locale(): Locale? {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return resources.configuration.locales.get(0)
        } else {
            return resources.configuration.locale
        }
    } catch (e: Exception) {
        Timber.e(e)
    }
    return null
}

inline fun ContextWrapper.showSimplyNotification(
    channelId: String,
    predicate: ContextWrapper.(NotificationCompat.Builder) -> Unit = {}
): Notification {
    val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return NotificationCompat.Builder(this, channelId)
        .setContentTitle("Title null")
        .setContentText("Content null")
        .setSmallIcon(android.R.drawable.stat_notify_more)
        .also {
            predicate(this@showSimplyNotification, it)
        }
        .build()
        .also {
            notificationManager.notify(1, it)
        }
}

inline fun Service.showSimplyForegroundNotification(
    channelId: String,
    predicate: Service.(NotificationCompat.Builder) -> Unit = {}
): Notification {
    return NotificationCompat.Builder(this, channelId)
        .setContentTitle("Title null")
        .setContentText("Content null")
        .setSmallIcon(android.R.drawable.stat_notify_more)
        .also {
            predicate(this@showSimplyForegroundNotification, it)
        }
        .build()
        .also {
            startForeground(1, it)
        }
}

/**
 * 判断当前应用是否是debug状态
 */
fun Application.isApkInDebug(): Boolean {
    try {
        val info = applicationInfo
        return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: Exception) {
        return false
    }
}

inline fun View.getCompatContext(): Context {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
        when (context) {
            is Activity -> {
                return context
            }
            is ContextWrapper -> {
                return (context as ContextWrapper).baseContext
            }
        }
    }
    return context
}
