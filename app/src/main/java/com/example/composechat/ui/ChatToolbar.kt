package com.example.composechat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composechat.ui.theme.ComposeChatTheme

@Composable
fun ChatToolbar(
    title: String,
    onToggleLogout: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 50.dp)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = onToggleLogout
        ) {
            Icon(
                imageVector = Icons.Filled.Person, // Lock
                contentDescription = "login / logout",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Preview
@Composable
fun ChatToolbarPreview() {
    ComposeChatTheme {
        ChatToolbar(
            title = "Welcome to the chat",
            onToggleLogout = {}
        )
    }
}