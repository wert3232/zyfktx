@file:Suppress("NOTHING_TO_INLINE")

package android.zyf.ktx

import android.annotation.SuppressLint
import android.zyf.java.LiveEventBus
import android.zyf.java.ReflectionUtil
import androidx.arch.core.internal.SafeIterableMap
import androidx.lifecycle.*
import androidx.annotation.MainThread
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class AweObserver<T : Any>(private val block: (T?) -> Unit) : Observer<T> {
    private var context: CoroutineContext = EmptyCoroutineContext
    private var supervisorJob = SupervisorJob(context[Job])
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Main + context + supervisorJob)
    private var delayTime: Long = 0
    private var timeout: Job? = null
    private var isDistinct = false
    private var isDebounce = false
    private var isThrottleLast = false
    private var oldValue: WeakReference<T?>? = null
    override fun onChanged(newValue: T?) {
        kotlin.run top@{
            if (isDistinct) {
                when {
                    oldValue?.get() == newValue -> return@top
                }
            }
            oldValue = WeakReference(newValue)

            //debounce
            if (isDebounce) {
                timeout?.cancel()
                timeout = scope.launch {
                    delay(delayTime)
                    block(newValue)
                }
            } else if (isThrottleLast) {
                if (timeout?.isActive != true) {
                    timeout = scope.launch {
                        delay(delayTime)
                        block(oldValue?.get())
                    }
                }
            } else {
                block(newValue)
            }
        }
    }

    fun distinct() = kotlin.run {
        isDistinct = true
        this
    }

    fun setCoroutineContext(context: CoroutineContext) = kotlin.run {
        this.context = context
        supervisorJob = SupervisorJob(context[Job])
        scope = CoroutineScope(Dispatchers.Main + context + supervisorJob)
        this@AweObserver
    }

    /*
    * time: millisecond
    * */
    fun debounce(time: Long) = kotlin.run {
        isDebounce = true
        delayTime = time
        this@AweObserver
    }

    private fun throttleLast(time: Long) = kotlin.run {
        isThrottleLast = true
        delayTime = time
        this@AweObserver
    }

    companion object {
        fun <T : Any> notNull(block: (T) -> Unit): AweObserver<T> {
            return AweObserver { value ->
                if (value != null) {
                    block(value)
                }
            }
        }
    }
}


abstract class AweViewModel : ViewModel() {
    private val list by lazy {
        mutableListOf<Pair<WeakReference<LiveData<out Any>>, WeakReference<Observer<out Any>>>>()
    }
    private val jobSet by lazy {
        mutableSetOf<WeakReference<Job>>()
    }
    private val taskSet by lazy {
        mutableSetOf<() -> Unit>()
    }

    fun <T> LiveData<T>.viewModelObserveAfter(observer: Observer<T?>) {
        this.observeForeverAfter(observer)
        this@AweViewModel.addClear(this, observer)
    }

    fun addClear(liveData: LiveData<*>, observer: Observer<*>) {
        run r@{
            list.forEach {
                if (observer.hashCode() == it.second.hashCode() && liveData.hashCode() == it.first.hashCode()) {
                    return@r
                }
            }
            list.add(Pair(WeakReference(liveData), WeakReference(observer)))
        }
    }

    fun addClear(job: Job) {
        run r@{
            jobSet.forEach {
                if (job.hashCode() == it.get().hashCode()) {
                    return@r
                }
            }
            jobSet.add(WeakReference(job))
        }
    }

    fun addClear(block: () -> Unit) {
        if (taskSet.contains(block)) return
        taskSet.add(block)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCleared() {
        list.forEach {
            val liveData = it.first.get()
            val observer = it.second.get()
            if (liveData != null && observer != null) {
                liveData as? LiveData<Any> ?: return
                observer as? Observer<Any> ?: return
                liveData.removeObserver(observer)
            }
        }
        jobSet.forEach { it ->
            it.get()?.cancel()
        }
        taskSet.forEach {
            it.invoke()
        }
        jobSet.clear()
        taskSet.clear()
        list.clear()
        super.onCleared()
    }
}

open class AweMutableLiveData<T> : MutableLiveData<T> {
    constructor(value: T) : super(value)
    constructor() : super()

    inline fun observeAfter(owner: LifecycleOwner, crossinline block: (T?) -> Unit) {
        observeAfter(owner, Observer {
            block(it)
        })
    }

    @MainThread
    inline fun observeForeverAfter(crossinline block: (T?) -> Unit) {
        observeForeverAfter(Observer {
            block(it)
        })
    }
}

@SuppressLint("RestrictedApi")
@Suppress("INACCESSIBLE_TYPE")
@MainThread
fun <T> LiveData<T>.observeAfter(owner: LifecycleOwner, observer: Observer<T?>) {
    if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
        return
    }
    val parameterTypes = arrayOf(
        androidx.lifecycle.LiveData::class.java,
        androidx.lifecycle.LifecycleOwner::class.java,
        androidx.lifecycle.Observer::class.java
    )
    val observerWrapper = ReflectionUtil.getObjectInstance(
        """androidx.lifecycle.LiveData${'$'}LifecycleBoundObserver""",
        parameterTypes,
        arrayOf(this, owner, observer)
    )
    val mVersion = ReflectionUtil.getFieldValue(this, "mVersion") as Int
    ReflectionUtil.setFieldValue(observerWrapper, "mLastVersion", mVersion)
    val mObservers = ReflectionUtil.getFieldValue(this, "mObservers") as SafeIterableMap<Any, Any>
    val existing = mObservers.putIfAbsent(observer, observerWrapper)
    if (existing != null) {
        val isAttached = ReflectionUtil.invokeMethod(
            existing,
            "isAttachedTo",
            arrayOf(LifecycleOwner::class.java),
            arrayOf(owner)
        ) as Boolean
        if (!isAttached) {
            throw IllegalArgumentException("Cannot add the same observer" + " with different lifecycles")
        }
        return
    }
    owner.lifecycle.addObserver(observerWrapper as LifecycleObserver)
}

/*
*只在接收会收注册Observer之后的数据
* 例如value a、b、(observeForeverAfter) c、d、e, 将只接手 c、d。e,没有b
* */
@SuppressLint("RestrictedApi")
@Suppress("INACCESSIBLE_TYPE")
@MainThread
fun <T> LiveData<T>.observeForeverAfter(observer: Observer<T?>) {
    val parameterTypes = arrayOf(
        androidx.lifecycle.LiveData::class.java,
        androidx.lifecycle.Observer::class.java
    )
    val wrapper = ReflectionUtil.getObjectInstance(
        """androidx.lifecycle.LiveData${'$'}AlwaysActiveObserver""",
        parameterTypes,
        arrayOf(this, observer)
    )
    val mVersion = ReflectionUtil.getFieldValue(this, "mVersion") as Int
    ReflectionUtil.setFieldValue(wrapper, "mLastVersion", mVersion)
    val mObservers = ReflectionUtil.getFieldValue(this, "mObservers") as SafeIterableMap<Any, Any>
    val existing = mObservers.putIfAbsent(observer, wrapper)
    if (existing != null) {
//            if (existing is LiveData.LifecycleBoundObserver) {
//                throw IllegalArgumentException("Cannot add the same observer with different lifecycles")
//            }
        return
    }
    ReflectionUtil.invokeMethod(wrapper, "activeStateChanged", arrayOf(Boolean::class.java), arrayOf(true))
}


inline fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, crossinline action: (T) -> Unit) =
    Observer<T> {
        if (it != null) {
            action(it)
        }
    }.also {
        runOnMain {
            observe(owner, Observer<T> {
                if (it != null) {
                    action(it)
                }
            })
        }
    }

fun <T> LiveData<T>.observeForeverNotNull(action: (T) -> Unit) = Observer<T> {
    if (it != null) {
        action(it)
    }
}.also {
    runOnMain {
        observeForever(it)
    }
}

inline fun <T> LiveData<T>.observeResumed(owner: LifecycleOwner, crossinline action: (T) -> Unit) = Observer<T> {
    if (it != null && owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
        action(it)
    }
}.also {
    runOnMain {
        observe(owner, it)
    }
}

inline fun <T> MutableLiveData<T>.postValueIfNotSame(_value: T) {
    if (_value != value) {
        postValue(_value)
    }
}

inline fun <T> MutableLiveData<T>.postSafeValueIfNotSame(_value: T) {
    postSafeValue(_value, true)
}

inline fun <T> MutableLiveData<T>.postSafeValue(_value: T, notSame: Boolean = false) {
    if (notSame && _value == value) return
    if (isMainThread()) {
        value = _value
    } else {
        postValue(_value)
    }
}


inline fun <reified T> LiveEventBus.withType(key: String) = this.with(key, T::class.java)!!

fun <T> LiveEventBus.Observable<T>.observe(aweViewModel: AweViewModel, observer: Observer<T>) {
    this.observeForever(observer)
    aweViewModel.addClear {
        this.removeObserver(observer)
    }
}