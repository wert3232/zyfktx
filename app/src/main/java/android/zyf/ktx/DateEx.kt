package android.zyf.ktx
/*

import org.joda.time.DateTime
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun currentTimeOf(dateFormat: String = "yyyy-MM-dd HH:mm:ss"): String {
    val df = SimpleDateFormat(dateFormat)
    return df.format(Date())
}

fun timestampToFormat(timestamp: Any?, dateFormat: String = "yyyy-MM-dd HH:mm", defaultValue: String): String {
    try {
        return run result@{
            val t = when (timestamp) {
                is Double -> timestamp.toLong()
                is String -> timestamp.toLongOrNull() ?: defaultValue
                is Long -> timestamp
                else -> return@result defaultValue
            }
            DateTime(t).toString(dateFormat)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return defaultValue
}*/
