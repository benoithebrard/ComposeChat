package com.example.composechat.data

import com.example.composechat.utils.QueryMatcher

data class ChatMessage(
    val user: ChatUser,
    val message: String,
    val time: Long
) : QueryMatcher {
    override fun doesMatchSearchQuery(query: String): Boolean {
        // add more complex matching logic here
        return message.contains(query, ignoreCase = true)
    }
}