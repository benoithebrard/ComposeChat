package com.example.composechat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composechat.data.ChatUser
import com.example.composechat.data.ChatUserPreview
import com.example.composechat.ui.theme.ComposeChatTheme
import kotlin.random.Random

private const val CROSSFADE_DURATION_ML = 500

@Composable
fun ChatUserIcon(
    preview: ChatUserPreview,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color(preview.user.color))
                .padding(bottom = 2.dp)
                .wrapContentSize(),
            text = preview.user.name.first().toString().uppercase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        if (preview.user.imageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(preview.user.imageUrl)
                    .crossfade(CROSSFADE_DURATION_ML)
                    .build(),
                contentDescription = "profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize()
            )
        }
    }
}

@Preview(widthDp = 40, heightDp = 40)
@Composable
fun ChatUserIconPreview() {
    val preview = ChatUserPreview(
        user = ChatUser(
            id = "1234",
            name = "Zoe",
            color = Random.nextLong(0xFFFFFFFF)
        ),
        lastMessage = "Hey wassup I'm waiting for you by the bus stop and it's raining like hell here so please come over really soon!"
    )
    ComposeChatTheme {
        ChatUserIcon(
            preview = preview
        )
    }
}