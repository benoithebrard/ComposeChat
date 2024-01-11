package com.example.composechat.data

import kotlin.random.Random

data class ChatUserPreview(
    val id: String = Random.nextInt().toString(),
    val user: ChatUser,
    val lastMessage: String?
)