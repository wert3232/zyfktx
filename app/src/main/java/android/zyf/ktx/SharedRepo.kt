package android.zyf.ktx

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.reflect.KClass

class SharedRepo(val shared: SharedPreferences) : CoroutineScope by MainScope() {
    private val onSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            launch {
                if (map.containsKey(key)) {
                    val (classType, liveData) = map.get(key) ?: return@launch
                    when (classType) {
                        Int::class -> {
                            val value = shared.getInt(key, 0)
                            liveData.value = value
                        }
                        Float::class -> {
                            val value = shared.getFloat(key, 0f)
                            liveData.value = value
                        }
                        String::class -> {
                            val value = shared.getString(key, "")
                            liveData.value = value
                        }
                        Long::class -> {
                            val value = shared.getLong(key, 0)
                            liveData.value = value

                        }
                        Boolean::class -> {
                            val value = shared.getBoolean(key, false)
                            liveData.value = value
                        }
                    }
                }
            }
        }

    val map = WeakHashMap<String, Pair<KClass<*>, AweMutableLiveData<*>>>()

    init {
        shared.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    fun stop() {
        cancel()
        shared.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    inline fun <reified T : Any> get(key: String): LiveData<T> {
        var action: (() -> T)? = null
        val legality = when (T::class) {
            Int::class -> {
                action = { shared.getInt(key, 0) as T }
                true
            }
            Float::class -> {
                action = { shared.getFloat(key, 0f) as T }
                true
            }
            String::class -> {
                action = { shared.getString(key, "") as T }
                true
            }
            Long::class -> {
                action = { shared.getLong(key, 0) as T }
                true
            }
            Boolean::class -> {
                action = { shared.getBoolean(key, false) as T }
                true
            }
            else -> false
        }
        assert(legality) {
            "This class ${T::class.qualifiedName} is not a legal SharedPreference generic"
        }
        if (!map.containsKey(key)) {
            val liveData = AweMutableLiveData<T>().also {
                it.value = action?.invoke()
            }
            val pair = T::class to liveData
            map.put(key, pair)
        }
        return map.get(key)!!.second as LiveData<T>
    }
}