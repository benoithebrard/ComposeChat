package com.example.composechat.state

import com.example.composechat.data.ChatUserPreview

data class ChatState(
    val userPreviews: List<ChatUserPreview>,
    val headerTitle: String
)