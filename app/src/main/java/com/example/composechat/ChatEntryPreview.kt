package com.example.composechat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlin.random.Random

@Composable
fun ChatEntryPreview(
    preview: ChatUserPreview,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .defaultMinSize(minWidth = 50.dp, minHeight = 50.dp)
                    .clip(CircleShape)
                    .background(Color(preview.user.color))
                    .padding(bottom = 2.dp)
                    .wrapContentSize(),
                text = preview.user.name.first().toString().uppercase(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ChatUserEntryPreview() {
    val preview = ChatUserPreview(
        user = ChatUser(
            id = "1234",
            name = "Zoe",
            color = Random.nextLong(0xFFFFFFFF)
        ),
        lastMessage = "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
    ComposeChatTheme {
        ChatEntryPreview(preview = preview)
    }
}