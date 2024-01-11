package com.example.composechat.state

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composechat.data.ChatMessage
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.utils.DebugUtils.generateMessage
import com.example.composechat.utils.DebugUtils.generateUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

private const val DEFAULT_SCREEN_TITLE = "Chat"

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
                                lastMessage = chatMessages.findLatestMessage(user)
                            )
                        },
                        headerTitle = users.firstOrNull()?.name?.capitalize(Locale.current)
                            ?: DEFAULT_SCREEN_TITLE
                    )
                }
            } else ChatState.LoggedOut
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ChatState.Empty)

    fun toggleLogout() {
        isLoggedIn.value = !isLoggedIn.value
    }

    fun removeUser(user: ChatUser) {
        users.value = users.value.minus(user)
    }

    // TODO: fetch data from backend instead, or provide UI to create messages
    fun createNewUserMessage() {
        val user = generateUser()
        val message1 = generateMessage(user)
        val message2 = generateMessage(user, 10L)
        users.value = listOf(user) + users.value
        chatMessages.value = listOf(message1, message2) + chatMessages.value
    }
}

private fun List<ChatMessage>.findLatestMessage(
    user: ChatUser
): String? = filter { it.user.id == user.id }
    .maxByOrNull { it.time }
    ?.message