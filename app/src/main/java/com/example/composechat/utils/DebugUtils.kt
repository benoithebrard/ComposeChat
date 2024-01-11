package com.example.composechat.utils

import com.example.composechat.data.ChatMessage
import com.example.composechat.data.ChatUser
import kotlin.random.Random

internal object DebugUtils {
    private var userCount = 0

    private val mockMessages: List<String> = listOf(
        "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!",
        "Hi mom. Sorry I won't make it for dinner tonight, I'm hanging out with my buddies instead :)",
        "SPAM DETECTED - don not open this message",
        "Thanks for your contribution to Vox News. We're happy you care about the world",
        "Yo man why don't you answer your phone???",
        "Hasta la vista, baby",
        "Food has been delivered to your doorstep"
    )


    fun generateUser(): ChatUser = ChatUser(
        id = Random.nextInt().toString(),
        name = "${('a'..'z').random()} - user #${userCount++}",
        color = Random.nextLong(0xFFFFFFFF)
    )

    fun generateMessage(user: ChatUser): ChatMessage = ChatMessage(
        user = user,
        message = mockMessages.shuffled().random(),
        time = System.currentTimeMillis()
    )
}