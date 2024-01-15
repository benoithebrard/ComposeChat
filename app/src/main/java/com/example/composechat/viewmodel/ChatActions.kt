package com.example.composechat.viewmodel

import com.example.composechat.data.ChatUser

interface ChatActions {
    fun toggleLogout()
    fun removeUser(user: ChatUser)
    fun createNewUserMessage()
    fun onSearchTextChanged(text: String)

    companion object {
        val DefaultChatActions = object : ChatActions {
            override fun toggleLogout() {}
            override fun removeUser(user: ChatUser) {}
            override fun createNewUserMessage() {}
            override fun onSearchTextChanged(text: String) {}
        }
    }
}