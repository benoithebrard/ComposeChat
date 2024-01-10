package com.example.composechat.state

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composechat.data.ChatMessage
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

class ChatViewModel : ViewModel() {

    private val isLoggedIn = MutableStateFlow(true)
    private val chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    private val users = MutableStateFlow<List<ChatUser>>(emptyList())

    val chatState: StateFlow<ChatState> =
        combine(isLoggedIn, chatMessages, users) { isLoggedIn, chatMessages, users ->
            if (isLoggedIn) {
                if (users.isEmpty()) {
                    ChatState.Empty
                } else {
                    ChatState.Content(
                        userPreviews = users.map { user ->
                            ChatUserPreview(
                                user = user,
                                lastMessage = chatMessages
                                    .filter { it.user.id == user.id }
                                    .maxByOrNull { it.time }
                                    ?.message
                            )
                        },
                        headerTitle = users.firstOrNull()?.name?.capitalize(Locale.current)
                            ?: "Chat"
                    )
                }
            } else ChatState.LoggedOut
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ChatState.Empty)

    fun toggleLogout() {
        isLoggedIn.value = !isLoggedIn.value
    }

    fun createNewUserMessage() {
        val user = generateUser()
        val message = generateMessage(user)
        addUser(user)
        addMessage(message)
    }

    fun refreshMessage(userId: String) {
        findUserById(userId)?.let { user ->
            addMessage(generateMessage(user))
        }
    }

    private fun addMessage(message: ChatMessage) {
        chatMessages.value = listOf(message) + chatMessages.value
    }

    private fun addUser(user: ChatUser) {
        users.value = listOf(user) + users.value
    }

    private fun findUserById(userId: String): ChatUser? = users.value.find { it.id == userId }
}

private var userCount = 0

private val mockMessages: List<String> = listOf(
    "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!",
    "Hi mom. Sorry I won't make it for dinner tonight, I'm hanging out with my buddies instead :)",
    "SPAM DETECTED - don not open this message",
    "Thanks for your contribution to Vox News. We're happy you care about the world",
    "Yo man why don't you answer your phone???",
    "Hasta la vista, baby"
)


internal fun generateUser(): ChatUser = ChatUser(
    id = Random.nextInt().toString(),
    name = "${('a'..'z').random()} - user #${userCount++}",
    color = Random.nextLong(0xFFFFFFFF)
)

private fun generateMessage(user: ChatUser): ChatMessage = ChatMessage(
    user = user,
    message = mockMessages.shuffled().random(),
    time = System.currentTimeMillis()
)