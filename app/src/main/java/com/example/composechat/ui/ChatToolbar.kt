package com.example.composechat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.state.ChatState
import com.example.composechat.ui.theme.ComposeChatTheme

internal const val DEFAULT_TOOLBAR_TITLE = "Chat"

@Composable
fun ChatToolbar(
    state: ChatState,
    onToggleLogout: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 50.dp)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = (state as? ChatState.Content)?.headerTitle ?: DEFAULT_TOOLBAR_TITLE,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart),
            onClick = onToggleLogout
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onToggleLogout
        ) {
            Icon(
                imageVector = if (state is ChatState.LoggedOut) {
                    Icons.Filled.Lock
                } else Icons.Filled.Person,
                contentDescription = "login / logout",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Preview
@Composable
fun ChatToolbarPreview() {
    ComposeChatTheme {
        val state = ChatState.Content(
            userPreviews = mockUserPreviews.take(2),
            headerTitle = "Welcome Back"
        )
        ComposeChatTheme {
            ChatToolbar(
                state = state
            )
        }
    }
}

@Preview
@Composable
fun ChatToolbarLoggedOutPreview() {
    ComposeChatTheme {
        ComposeChatTheme {
            ChatToolbar(
                state = ChatState.LoggedOut
            )
        }
    }
}