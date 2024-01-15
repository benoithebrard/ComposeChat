package com.example.composechat.viewmodel

import com.example.composechat.data.ChatUser

interface ChatActions {
    fun toggleLogout()
    fun removeUser(user: ChatUser)
    fun createNewUserMessage()
    fun onSearchTextChanged(text: String)

    companion object {
        val DefaultChatActions = object : ChatActions {
            override fun toggleLogout() {
                TODO("Not yet implemented")
            }

            override fun removeUser(user: ChatUser) {
                TODO("Not yet implemented")
            }

            override fun createNewUserMessage() {
                TODO("Not yet implemented")
            }

            override fun onSearchTextChanged(text: String) {
                TODO("Not yet implemented")
            }
        }
    }
}