package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composechat.data.ChatMessage
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.state.ChatViewModel
import com.example.composechat.ui.ChatScreen
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChatTheme {
                val viewModel = viewModel<ChatViewModel>()
                val state by viewModel.chatState.collectAsStateWithLifecycle()
                ChatScreen(
                    state = state,
                    onCreateNewUserMessage = {
                        val user = generateUser()
                        val message = generateMessage(user)
                        viewModel.apply {
                            addUser(user)
                            addMessage(message)
                        }
                    },
                    onToggleLogout = {
                        viewModel.toggleLogout()
                    }
                )
            }
        }
    }
}

var userCount = 0

private fun generateUser(): ChatUser = ChatUser(
    id = Random.nextInt().toString(),
    name = "${('a'..'z').random()} - user #${userCount++}",
    color = Random.nextLong(0xFFFFFFFF)
)

private fun generateMessage(user: ChatUser): ChatMessage = ChatMessage(
    user = user,
    message = "$userCount - Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!",
    time = System.currentTimeMillis()
)

internal val mockUserPreviews = (1..100).map { index ->
    ChatUserPreview(
        user = generateUser(),
        lastMessage = "$index - Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
}