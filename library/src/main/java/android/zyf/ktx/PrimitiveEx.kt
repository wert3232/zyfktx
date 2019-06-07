@file:Suppress("NOTHING_TO_INLINE")

package android.zyf.ktx

import java.lang.Exception

fun Any.toUnsafeInt(): Int {
    return when (val value = this) {
        is String -> {
            value.toInt()
        }
        is Int -> {
            value
        }
        is Float -> {
            value.toInt()
        }
        is Double -> {
            value.toInt()
        }
        is Long -> {
            value.toInt()
        }
        else -> throw NumberFormatException()
    }
}

fun String.toInt(defaultInt: Int): Int {
    return this.toIntOrNull() ?: defaultInt
}

fun Any.toNumberOrNull(): Number? {
    return when (this) {
        is Number -> {
            this
        }
        is String -> {
            if (this.toIntOrNull() != null) {
                this as Number
            } else if (this.toLongOrNull() != null) {
                this as Number
            } else if (this.toFloatOrNull() != null) {
                this as Number
            } else null
        }
        else -> null
    }
}

fun Any.toBooleanOrNull() = when {
    this is String -> {
        when {
            this.equals("true", true) -> true
            this.equals("false", true) -> false
            this.equals("1", true) -> true
            this.equals("0", true) -> false
            else -> null
        }
    }
    this is Boolean -> this
    this is Number -> {
        when {
            this == 1 -> true
            this == 0 -> false
            else -> null
        }
    }
    else -> null
}

inline fun MutableMap<String, Any>.transform(key: String, block: (Any?) -> String) {
    val t = get(key)
    this.put(key, block.invoke(t))
}

inline fun <reified T : Any> MutableMap<String, Any>.makeTo(fromKey: String, toKey: String, block: (from: Any?) -> T) {
    val from = get(fromKey)
    this.put(toKey, block.invoke(from))
}

fun <T, S> MutableList<T>.compose(
    mixList: List<S>,
    mixFilter: (T, S) -> Boolean,
    mixOperate: (T?, S) -> T,
    removeCannotMix: Boolean = true
) {
    val list1 = this
    val list2 = mutableListOf<S>().apply {
        addAll(mixList)
    }
    for (i in list1.size - 1 downTo 0) {
        run {
            val item1 = list1[i]
            for (j in list2.size - 1 downTo 0) {
                val item2 = list2[j]
                //判断是否能融合
                val mix = mixFilter(item1, item2)
                if (mix) {
                    list1[i] = mixOperate(item1, item2)
                    list2.removeAt(j)
                    return@run
                }
            }

            //把不能融合的对象移除掉
            if (removeCannotMix) {
                list1.remove(item1)
            }
        }
    }
    list1.addAll(0, list2.map {
        mixOperate(null, it)
    })
}

infix fun Long.pow(n: Number): Long {
    return Math.pow(this.toDouble(), n.toDouble()).toLong()
}

infix fun Int.pow(n: Number): Long {
    return Math.pow(this.toDouble(), n.toDouble()).toLong()
}

//后n位抹零
infix fun Int.erase(n: Int): Int {
    val p = 10 pow n
    if (p > Int.MAX_VALUE) {
        throw  IndexOutOfBoundsException("divisor: $p > Int.MAX_VALUE")
    }
    val divisor = p.toInt()
    return (this / divisor) * divisor
}


fun Long.toFileFormat(decimalNum: Int = 2): String {
    val value = this
    val formString = "%.${decimalNum}f"
    return when (value) {
        in 0 until (1024 pow 1) -> {
            """${value} B"""
        }
        in (1024 pow 1) until (1024 pow 2) -> {
            """${formString.format(value.toDouble() / (1024 pow 1))} KB"""
        }
        in (1024 pow 2) until (1024 pow 3) -> {
            """${formString.format(value.toDouble() / (1024 pow 2))} MB"""
        }
        in (1024 pow 3) until Long.MAX_VALUE -> {
            """${formString.format(value.toDouble() / (1024 pow 3))} GB"""
        }
        else -> {
            "ERROR"
        }
    }
}

inline fun <T> mutableListCreate(size: Int, create: (index: Int) -> T): MutableList<T> {
    val list = ArrayList<T>()
    for (index in 0 until size) {
        list.add(create(index))
    }
    return list
}

fun Int.toHex() = String.format("%X", this)
fun ByteArray.toString(separation: String = " ") = this.let { array ->
    var result = ""
    array.forEach {
        result += String.format("%02X", it) + separation
    }
    result.trim()
}

inline fun <reified T : Any> T.toArray() = arrayOf<T>(this)
inline fun <reified T : Any> T.toList() = arrayOf<T>(this).toMutableList()
infix operator fun Byte.plus(bytes: ByteArray) = ByteArray(1 + bytes.size) {
    if (it == 0) {
        this@plus
    } else {
        bytes[it - 1]
    }
}

infix operator fun ByteArray.plus(byte: Byte) = ByteArray(this@plus.size + 1) { index ->
    if (index < this@plus.size) {
        this[index]
    } else if (index == this@plus.size) {
        byte
    } else {
        throw ArrayIndexOutOfBoundsException("ByteArray.plus return error with index:$index")
    }
}

infix operator fun Int.plus(bytes: ByteArray): ByteArray {
    if (this@plus > 0XFF) {
        throw IndexOutOfBoundsException("An error occurred while int converting Byte, Int > 0XFF,please check index!!!")
    } else return this@plus.toByte() + bytes
}

infix operator fun ByteArray.plus(int: Int): ByteArray {
    if (int > 0XFF) {
        throw IndexOutOfBoundsException("An error occurred while int converting Byte, ${int} > 0XFF,please check index!!!")
    } else return this@plus + int.toByte()
}

/**
 * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
 * @param value
 * 要转换的int值
 * @return byte数组
 */
/*fun Int.toBytes1(): ByteArray {
    val src = ByteArray(4)
    src[3] = (this shr 24 and 0xFF).toByte()
    src[2] = (this shr 16 and 0xFF).toByte()
    src[1] = (this shr 8 and 0xFF).toByte()
    src[0] = (this and 0xFF).toByte()
    return src
}*/

/**
 * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
 * 用toBytes
 */
/*fun Int.toBytes2(): ByteArray {
    val src = ByteArray(4)
    src[0] = (this shr 24 and 0xFF).toByte()
    src[1] = (this shr 16 and 0xFF).toByte()
    src[2] = (this shr 8 and 0xFF).toByte()
    src[3] = (this and 0xFF).toByte()
    return src
}*/
fun Int.toBytes(byteArraySize: Int = 4, isHighFront: Boolean = true): ByteArray {
    val size = if (byteArraySize in 1..4) {
        byteArraySize
    } else {
        4
    }
    val src = ByteArray(size)
    for (index in 0 until size) {
        if (index == size - 1) {
            src[index] = (this and 0xFF).toByte()
        } else {
            src[index] = (this shr (8 * (size - 1 - index)) and 0xFF).toByte()
        }
    }
    return src
}

fun ByteArray.toInt(isHighFront: Boolean = true): Int {
    val array = if (isHighFront) {
        this.reversedArray()
    } else {
        this
    }
    var temp = (array[0].toInt() and 0xff)
    val rangeSize = if (array.size <= 4) array.size else 4
    for (index in 1 until rangeSize) {
        temp = temp or (array[index].toInt() and 0xff shl 8 * index)
    }
    return temp
}

inline fun Float.to1f() = String.format("%.1f", this)

