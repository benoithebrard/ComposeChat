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
                    },
                    onPreviewSelected = { userId ->
                        viewModel.findUserById(userId)?.let { user ->
                            viewModel.addMessage(generateMessage(user))
                        }
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

private val mockMessages: List<String> = listOf(
    "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!",
    "Hi mom. Sorry I won't make it for dinner tonight, I'm hanging out with my buddies instead :)",
    "SPAM DETECTED - don not open this message",
    "Thanks for your contribution to Vox News. We're happy you care about the world",
    "Yo man why don't you answer your phone???",
    "Hasta la vista, baby"
)

private fun generateMessage(user: ChatUser): ChatMessage = ChatMessage(
    user = user,
    message = mockMessages.shuffled().random(),
    time = System.currentTimeMillis()
)

internal val mockUserPreviews = (1..100).map { index ->
    ChatUserPreview(
        user = generateUser(),
        lastMessage = "$index - Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
}