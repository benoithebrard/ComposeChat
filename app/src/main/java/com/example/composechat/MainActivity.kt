package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.ui.theme.ComposeChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChatTheme {
                ChatScreen()
            }
        }
    }
}

@Composable
fun ChatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 50.dp)
                .background(MaterialTheme.colorScheme.primary)
                .wrapContentHeight(),
            text = "Chat",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Box(
            Modifier
                .fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .align(Alignment.BottomEnd),
                onClick = {
                    // add new messages
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add new message",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ComposeChatTheme {
        ChatScreen()
    }
}