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
    val loadable = remember(loader) { LoadableState(
        mutableStateOf(true),
        mutableStateOf(null),
        loader
    ) }
    LaunchedEffect(Unit) { loadable.reload() }
    return loadable
}

open class LoadableState<T: Any>
internal constructor(
    private val loadingState: MutableState<Boolean> = mutableStateOf(true),
    private val state: MutableState<T?> = mutableStateOf(null),
    private val load: suspend () -> T
) {
    val loading by loadingState
    val nullableValue by state
    open val value: T get() = state.value ?: throw IllegalStateException("Value is loading")

    suspend fun reload(clearState: Boolean = false) {
        loadingState.value = true
        if (clearState) state.value = null
        backgroundReload()
    }

    suspend fun backgroundReload() {
        withContext(Dispatchers.IO) { state.value = load() }
        loadingState.value = false
    }
}
