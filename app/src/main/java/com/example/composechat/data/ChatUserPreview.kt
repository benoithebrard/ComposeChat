package com.example.composechat.data

import androidx.compose.runtime.Stable
import kotlin.random.Random

@Stable
data class ChatUserPreview(
    val id: String = Random.nextInt().toString(),
    val user: ChatUser,
    val lastMessage: String?
)