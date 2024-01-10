package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.state.ChatState
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val state = ChatState(
            userPreviews = mockUserPreviews,
            headerTitle = "message #420"
        )
        setContent {
            ComposeChatTheme {
                ChatScreen(
                    state = state
                )
            }
        }
    }
}

@Composable
fun ChatScreen(
    state: ChatState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        ChatToolbar(
            title = state.headerTitle,
            onToggleLogout = {
                // toggle logout
            }
        )
        Box {
            ChatPreviewEntries(
                entries = state.userPreviews,
                onEntrySelected = { userId ->
                    // add user maybe
                }
            )
            IconButton(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .align(Alignment.BottomEnd),
                onClick = {
                    // add new messages
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add new message",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    val state = ChatState(
        userPreviews = mockUserPreviews,
        headerTitle = "message #420"
    )
    ComposeChatTheme {
        ChatScreen(
            state = state
        )
    }
}

val mockUserPreviews = (1..100).map { index ->
    ChatUserPreview(
        user = ChatUser(
            id = "user:$index",
            name = ('a'..'z').random().toString(),
            color = Random.nextLong(0xFFFFFFFF)
        ),
        lastMessage = "$index - Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
}