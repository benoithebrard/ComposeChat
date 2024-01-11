package com.example.composechat.state

import androidx.compose.runtime.Immutable
import com.example.composechat.data.ChatUserPreview

sealed class ChatState {

    @Immutable
    data class Content(
        val userPreviews: List<ChatUserPreview>,
        val headerTitle: String?
    ) : ChatState()

    object Empty : ChatState()

    object LoggedOut : ChatState()
}