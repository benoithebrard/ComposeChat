package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composechat.state.ChatViewModel
import com.example.composechat.ui.ChatScreen
import com.example.composechat.ui.theme.ComposeChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChatTheme {
                val viewModel = viewModel<ChatViewModel>()
                val state by viewModel.chatState.collectAsStateWithLifecycle()
                val searchText by viewModel.searchText.collectAsStateWithLifecycle()
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

                ChatScreen(
                    state = state,
                    isLoading = isLoading,
                    searchText = searchText,
                    onSearchTextChanged = viewModel::onSearchTextChanged,
                    onToggleLogout = {
                        viewModel.toggleLogout()
                    },
                    onCreateNewUserMessage = {
                        viewModel.createNewUserMessage()
                    },
                    onUserSelected = { user ->
                        viewModel.removeUser(user)
                    }
                )
            }
        }
    }
}