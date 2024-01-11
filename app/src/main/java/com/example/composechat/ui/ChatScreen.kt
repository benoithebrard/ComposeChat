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
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.state.ChatState
import com.example.composechat.ui.theme.ComposeChatTheme
import com.example.composechat.utils.DebugUtils.generateUser

@Composable
fun ChatScreen(
    state: ChatState,
    onCreateNewUserMessage: () -> Unit = {},
    onToggleLogout: () -> Unit = {},
    onUserSelected: (ChatUser) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        ChatToolbar(
            state = state,
            onToggleLogout = onToggleLogout
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state) {
                is ChatState.LoggedOut -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Logged out"
                        )
                    }
                }

                is ChatState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No message"
                        )
                    }
                }

                is ChatState.Content -> {
                    ChatPreviewEntries(
                        entries = state.userPreviews,
                        onUserSelected = onUserSelected
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .align(Alignment.BottomEnd),
                onClick = onCreateNewUserMessage
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add new message",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

internal val mockUserPreviews = (1..100).map { index ->
    ChatUserPreview(
        user = generateUser(),
        lastMessage = "$index - Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
}

@Preview
@Composable
fun ChatScreenPreview() {
    val state = ChatState.Content(
        userPreviews = mockUserPreviews.take(2),
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
            state = ChatState.Empty
        )
    }
}

@Preview
@Composable
fun ChatScreenLoggedOutPreview() {
    ComposeChatTheme {
        ChatScreen(
            state = ChatState.LoggedOut
        )
    }
}
