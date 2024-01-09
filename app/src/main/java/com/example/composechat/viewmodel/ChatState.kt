package com.example.composechat.viewmodel

import com.example.composechat.data.ChatUserPreview

data class ChatState(
    val userPreviews: List<ChatUserPreview>,
    val headerTitle: String
)