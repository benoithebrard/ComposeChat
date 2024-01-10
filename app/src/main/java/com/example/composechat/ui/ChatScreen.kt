package com.example.composechat.ui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.mockUserPreviews
import com.example.composechat.state.ChatState
import com.example.composechat.ui.theme.ComposeChatTheme

@Composable
fun ChatScreen(
    state: ChatState?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        ChatToolbar(
            title = state?.headerTitle ?: "Chat",
            onToggleLogout = {
                // toggle logout
            }
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state != null) {
                ChatPreviewEntries(
                    entries = state.userPreviews,
                    onEntrySelected = { userId ->
                        // add user maybe
                    }
                )
            } else {
                Text(
                    text = "No message"
                )
            }
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
        headerTitle = "Welcome"
    )
    ComposeChatTheme {
        ChatScreen(
            state = state
        )
    }
}

@Preview
@Composable
fun ChatScreenEmptyPreview() {
    ComposeChatTheme {
        ChatScreen(
            state = null
        )
    }
}
