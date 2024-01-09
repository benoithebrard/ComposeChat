package com.example.composechat.data

data class ChatMessage(
    val user: ChatUser,
    val message: String,
    val time: Long
)