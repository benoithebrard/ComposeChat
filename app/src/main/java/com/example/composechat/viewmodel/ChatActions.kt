package com.example.composechat.viewmodel

import com.example.composechat.data.ChatUser

interface ChatActions {
    val toggleLogout: () -> Unit
        get() = {}

    val removeUser: (user: ChatUser) -> Unit
        get() = {}

    val createNewUserMessage: () -> Unit
        get() = {}

    val onSearchTextChanged: (text: String) -> Unit
        get() = {}

    companion object {
        val DefaultChatActions = object : ChatActions {}
    }
}