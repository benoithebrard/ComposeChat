package com.example.composechat.data

import kotlin.random.Random

data class ChatUser(
    val id: String = Random.nextInt().toString(),
    val name: String,
    val color: Long
)