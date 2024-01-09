package com.example.composechat.state

import androidx.compose.runtime.Immutable
import com.example.composechat.data.ChatUserPreview

@Immutable
data class ChatState(
    val userPreviews: List<ChatUserPreview>,
    val headerTitle: String
)