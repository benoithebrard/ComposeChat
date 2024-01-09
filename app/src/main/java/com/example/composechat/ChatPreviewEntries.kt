package com.example.composechat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlin.random.Random

@Composable
fun ChatPreviewEntries(
    entries: List<ChatUserPreview>,
    onEntrySelected: (userId: String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = entries,
            key = { preview ->
                preview.user.id
            }
        ) { preview ->
            ChatPreviewEntry(
                preview = preview,
                onEntrySelected = {
                    onEntrySelected(preview.user.id)
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
            entries = (1..100).map { index ->
                ChatUserPreview(
                    user = ChatUser(
                        id = "user:$index",
                        name = ('a'..'z').random().toString(),
                        color = Random.nextLong(0xFFFFFFFF)
                    ),
                    lastMessage = "LastMessage#user:${index}"
                )
            },
            onEntrySelected = {}
        )
    }
}