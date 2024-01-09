package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.ui.theme.ComposeChatTheme
import com.example.composechat.viewmodel.ChatState
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val state = ChatState(
            userPreviews = (1..100).map { index ->
                ChatUserPreview(
                    user = ChatUser(
                        id = "user:$index",
                        name = "User#$index",
                        color = Random.nextLong(0xFFFFFFFF)
                    ),
                    lastMessage = "LastMessage#user:${index}"
                )
            },
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
        Box(
            Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 50.dp)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    // add user
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chat",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = {
                    // add new messages
                }) {
                Icon(
                    imageVector = Icons.Filled.Person, // Lock
                    contentDescription = "login / logout",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
        Box(
            Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.userPreviews,
                    key = { preview ->
                        preview.user.id
                    }
                ) { preview ->
                    ChatPreviewEntry(
                        preview = preview
                    )
                }
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
        userPreviews = (1..100).map { index ->
            ChatUserPreview(
                user = ChatUser(
                    id = "user:$index",
                    name = ('a'..'z').random().toString(),
                    color = Random.nextLong(0xFFFFFFFF)
                ),
                lastMessage = "LastMessage#user:${index}"
            )
        },
        headerTitle = "message #420"
    )
    ComposeChatTheme {
        ChatScreen(
            state = state
        )
    }
}