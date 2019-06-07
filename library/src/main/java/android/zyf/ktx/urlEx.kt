package android.zyf.ktx

import android.R.attr.data
import android.R.attr.keySet
import java.net.URLEncoder


fun getRequestUrl(url: String, vararg fields: Pair<String, Any>): String {

    val param = StringBuffer()
    for (item in fields) {
        param.append(item.first + "=" + URLEncoder.encode(item.second.toString(), "UTF-8") + "&")
    }
    val paramStr = param.toString().let {
        it.substring(0, it.length - 1)
    }
    return if (url.indexOf("?") >= 0) {
        "$url&$paramStr"
    } else {
        "$url?$paramStr"
    }
}
