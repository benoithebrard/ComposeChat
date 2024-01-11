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
private const val SUBSCRIPTION_TIMEOUT_MS = 5000L

class ChatViewModel : ViewModel() {

    private val isLoggedIn = MutableStateFlow(true)
    private val chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    private val users = MutableStateFlow<List<ChatUser>>(emptyList())
    private val searchText = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)

    val chatState: StateFlow<ChatState> = combine(
        isLoggedIn,
        chatMessages,
        users,
        searchText
    ) { isLoggedIn, chatMessages, users, searchText ->
        when {
            !isLoggedIn -> {
                ChatState.LoggedOut
            }

            users.isEmpty() -> {
                ChatState.Empty
            }

            searchText.isBlank() -> {
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

            else -> {
                val filteredMessages = chatMessages.filter { message ->
                    message.doesMatchSearchQuery(searchText) && users.contains(message.user)
                }
                if (filteredMessages.isNotEmpty()) {
                    ChatState.Content(
                        userPreviews = filteredMessages.map { message ->
                            ChatUserPreview(
                                user = message.user,
                                lastMessage = message.message
                            )
                        },
                        headerTitle = "Search $searchText"
                    )
                } else ChatState.Empty
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MS),
        ChatState.Empty
    )

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

    fun onSearchTextChanged(text: String) {
        searchText.value = text
    }
}

private fun List<ChatMessage>.findLatestMessage(
    user: ChatUser
): String? = filter { it.user.id == user.id }
    .maxByOrNull { it.time }
    ?.message