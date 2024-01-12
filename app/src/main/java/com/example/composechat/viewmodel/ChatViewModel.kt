package com.example.composechat.viewmodel

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composechat.data.ChatMessage
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.utils.DebugUtils.generateMessage
import com.example.composechat.utils.DebugUtils.generateUser
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

private const val SUBSCRIPTION_TIMEOUT_MS = 5000L
private const val SEARCH_DEBOUNCE_MS = 500L

class ChatViewModel : ViewModel() {

    private val isLoggedIn = MutableStateFlow(true)
    private val chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    private val users = MutableStateFlow<List<ChatUser>>(emptyList())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @OptIn(FlowPreview::class)
    val chatState: StateFlow<ChatState> = combine(
        isLoggedIn,
        chatMessages,
        users,
        searchText
            .debounce(SEARCH_DEBOUNCE_MS)
            .onEach { _isLoading.update { true } }
    ) { isLoggedIn, chatMessages, users, searchText ->
        when {
            !isLoggedIn -> {
                ChatState.LoggedOut
            }

            users.isEmpty() -> {
                ChatState.Empty
            }

            searchText.isNotBlank() -> {
                // simulate backend request
                delay(1000L)
                resolveSearchState(users, chatMessages, searchText)
            }

            else -> {
                resolveDefaultState(users, chatMessages)
            }
        }
    }
        .onEach { _isLoading.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT_MS),
            ChatState.Empty
        )

    private fun resolveDefaultState(
        users: List<ChatUser>,
        chatMessages: List<ChatMessage>
    ) = ChatState.Content(
        userPreviews = users.map { user ->
            ChatUserPreview(
                user = user,
                lastMessage = chatMessages.findLatestMessage(user)
            )
        },
        headerTitle = users.firstOrNull()?.name?.capitalize(Locale.current)
    )

    private fun resolveSearchState(
        users: List<ChatUser>,
        chatMessages: List<ChatMessage>,
        searchText: String
    ): ChatState {
        val filteredMessages = chatMessages.filter { message ->
            message.doesMatchSearchQuery(searchText) && users.contains(message.user)
        }
        return if (filteredMessages.isNotEmpty()) {
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
        _searchText.value = text
    }
}

private fun List<ChatMessage>.findLatestMessage(
    user: ChatUser
): String? = filter { it.user.id == user.id }
    .maxByOrNull { it.time }
    ?.message