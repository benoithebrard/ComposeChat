package com.example.composechat.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlin.random.Random

@Composable
fun ChatPreviewEntry(
    preview: ChatUserPreview,
    onPreviewSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .semantics {
                contentDescription = "chat preview entry"
            },
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Surface(
            modifier = Modifier.clickable {
                onPreviewSelected()
            })
        {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChatPreviewIcon(
                    preview = preview,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .wrapContentSize(),
                    text = preview.lastMessage ?: "no message",
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatPreviewEntryPreview() {
    val preview = ChatUserPreview(
        user = ChatUser(
            id = "1234",
            name = "Zoe",
            color = Random.nextLong(0xFFFFFFFF)
        ),
        lastMessage = "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
    ComposeChatTheme {
        ChatPreviewEntry(
            preview = preview,
            onPreviewSelected = {}
        )
    }
}