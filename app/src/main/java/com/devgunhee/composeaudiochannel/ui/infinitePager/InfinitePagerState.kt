package com.devgunhee.composeaudiochannel.ui.infinitePager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable

@ExperimentalFoundationApi
@Composable
fun rememberInfinitePagerState(
    itemCount: () -> Int,
    initialPage: Int = (Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2) % itemCount(),
    initialPageOffsetFraction: Float = 0f,
    pageCount: () -> Int = { Int.MAX_VALUE }
): InfinitePagerState {
    return rememberSaveable(saver = InfinitePagerStateImpl.Saver) {
        InfinitePagerStateImpl(
            itemCount,
            initialPage,
            initialPageOffsetFraction,
            pageCount
        )
    }.apply {
        pageCountState.value = pageCount
        itemCountState.value = itemCount
    }
}

@ExperimentalFoundationApi
internal class InfinitePagerStateImpl(
    updatedItemCount: () -> Int,
    initialPage: Int,
    initialPageOffsetFraction: Float,
    updatedPageCount: () -> Int
) : InfinitePagerState(initialPage, initialPageOffsetFraction) {

    var pageCountState = mutableStateOf(updatedPageCount)
    var itemCountState = mutableStateOf(updatedItemCount)

    override val pageCount: Int get() = pageCountState.value.invoke()
    override val itemCount: Int get() = itemCountState.value.invoke()
    override val middlePage: Int get() = (settledPage + 2) % itemCount

    companion object {
        /**
         * To keep current page and current page offset saved
         */
        val Saver: Saver<InfinitePagerStateImpl, *> = listSaver(
            save = {
                listOf(
                    it.itemCount,
                    it.currentPage,
                    it.currentPageOffsetFraction,
                    it.pageCount,
                )
            },
            restore = {
                InfinitePagerStateImpl(
                    updatedItemCount = { it[0] as Int },
                    initialPage = it[1] as Int,
                    initialPageOffsetFraction = it[2] as Float,
                    updatedPageCount = { it[3] as Int },
                )
            }
        )
    }
}

@ExperimentalFoundationApi
abstract class InfinitePagerState(
    initialPage: Int = 0,
    initialPageOffsetFraction: Float = 0f
) : PagerState(initialPage, initialPageOffsetFraction) {
    abstract val itemCount: Int
    abstract val middlePage: Int
}