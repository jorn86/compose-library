package org.hertsig.compose.component

import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun <T: Any> Loader(
    state: LoadableState<T>,
    loadingContent: @Composable () -> Unit = { LoadingIndicator() },
    content: @Composable (T) -> Unit
) {
    with(state.nullableValue) {
        if (this == null) {
            loadingContent()
        } else {
            content(this)
        }
    }
}

@Composable
fun <T: Any> rememberLoadableState(loader: suspend () -> T): LoadableState<T> {
    // Don't depend `remember` on `loader`, that may cause unintended new (unloaded) instances
    val loadable = remember {
        LoadableState(mutableStateOf(true), mutableStateOf(null), loader)
    }
    LaunchedEffect(Unit) { loadable.reload() }
    return loadable
}

open class LoadableState<T: Any>
internal constructor(
    loadingState: MutableState<Boolean> = mutableStateOf(true),
    state: MutableState<T?> = mutableStateOf(null),
    private val load: suspend () -> T
) {
    var loading by loadingState; private set
    var nullableValue by state; private set
    open val value: T get() = nullableValue ?: throw IllegalStateException("Value is loading")

    suspend fun reload(clearState: Boolean = false) {
        loading = true
        if (clearState) nullableValue = null
        backgroundReload()
    }

    suspend fun backgroundReload() {
        withContext(Dispatchers.IO) { nullableValue = load() }
        loading = false
    }

    override fun toString() = "LoadableState[loading=$loading, value=$nullableValue]"
}
