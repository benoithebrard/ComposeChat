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

    fun addMessage(message: ChatMessage) {
        chatMessages.value = listOf(message) + chatMessages.value
    }

    fun addUser(user: ChatUser) {
        users.value = listOf(user) + users.value
    }

    fun findUserById(userId: String): ChatUser? = users.value.find { it.id == userId }
}