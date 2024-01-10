package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.state.ChatState
import com.example.composechat.ui.ChatScreen
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