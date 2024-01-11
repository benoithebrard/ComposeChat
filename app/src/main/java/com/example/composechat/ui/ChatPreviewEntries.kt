package com.example.composechat.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatPreviewEntries(
    entries: List<ChatUserPreview>,
    onUserSelected: (ChatUser) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(entries) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collectLatest { index ->
                if (index <= 1) {
                    // scroll to top if the list changes while the top is visible
                    lazyListState.scrollToItem(0)
                }
            }
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = entries,
            key = { preview ->
                preview.id
            }
        ) { preview ->
            ChatPreviewEntry(
                preview = preview,
                onPreviewSelected = {
                    onUserSelected(preview.user)
                }
            )
        }
    }
}

@Preview
@Composable
fun ChatPreviewEntriesPreview() {
    ComposeChatTheme {
        ChatPreviewEntries(
            entries = mockUserPreviews.take(2),
            onUserSelected = {}
        )
    }
}