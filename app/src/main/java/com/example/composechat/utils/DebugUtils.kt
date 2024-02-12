package com.example.composechat.utils

import com.example.composechat.data.ChatMessage
import com.example.composechat.data.ChatUser
import kotlin.random.Random

internal object DebugUtils {
    private var userCount = 0

    private val mockMessages: List<String> = listOf(
        "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!",
        "Hi mom. Sorry I won't make it for dinner tonight, I'm hanging out with my buddies instead :)",
        "SPAM DETECTED - do not open this message",
        "Thanks for your contribution to Vox News. We're happy you care about the world",
        "Yo man why don't you answer your phone???",
        "Hasta la vista, baby",
        "Food has been delivered to your doorstep"
    )

    private val mockProfileImages: List<String> = listOf(
        "https://fastly.picsum.photos/id/955/200/200.jpg?hmac=_m3ln1pswsR9s9hWuWrwY_O6N4wizKmukfhvyaTrkjE",
        "https://fastly.picsum.photos/id/961/200/200.jpg?hmac=gHwrXvhjUL97oGKmAYQn508wdQ_V5sE9P64erzR-Ork",
        "https://fastly.picsum.photos/id/158/200/200.jpg?hmac=8rukQaKQTs6njn3-TO5g1J8hhSqM3Ria1Q9nzz8DZSI",
        "https://fastly.picsum.photos/id/905/200/200.jpg?hmac=PxOdpEd_gpj1SeeC1DMysEuacd5-lp9jjs1wfD4ds74",
        "https://fastly.picsum.photos/id/51/200/200.jpg?hmac=AxDMciMBjL8UIKzxl80paiBUywP7elWptqQW_qTq8vw",
        "https://fastly.picsum.photos/id/482/200/200.jpg?hmac=_oPafYMoTEVlnjtzFvYD5JoiK6Q-xfxbki4tYEMuGRI",
        "https://fastly.picsum.photos/id/220/200/200.jpg?hmac=1eed0JUIOlpc-iGslem_jB1FORVXUdRtOmgpHxDDKZQ",
        "https://fastly.picsum.photos/id/879/200/200.jpg?hmac=_4fWz44KoPcfzc5VRuEhms_-fXjdx1VsijYO3xVD9b0"
    )

    fun generateUser(): ChatUser = ChatUser(
        name = "${('a'..'z').random()} - user #${userCount++}",
        color = Random.nextLong(0xFFFFFFFF),
        imageUrl = if (userCount % 3 == 0) {
            mockProfileImages.shuffled().first()
        } else null
    )

    fun generateMessage(
        user: ChatUser,
        timeOffsetMillis: Long = 0L
    ): ChatMessage = ChatMessage(
        user = user,
        message = mockMessages.shuffled().random(),
        time = System.currentTimeMillis() + timeOffsetMillis
    )
}