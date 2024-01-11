package com.example.composechat

import androidx.compose.runtime.getValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composechat.state.ChatState
import com.example.composechat.state.ChatViewModel
import com.example.composechat.ui.ChatScreen
import com.example.composechat.ui.theme.ComposeChatTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ChatScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialStateTest() {
        composeTestRule.setContent {
            ComposeChatTheme {
                ChatScreen(
                    state = ChatState.Empty
                )
            }
        }

        composeTestRule.onNodeWithText("No message").assertIsDisplayed()
    }

    @Test
    fun logoutTest() {
        composeTestRule.setContent {
            ComposeChatTheme {
                ChatScreen(
                    state = ChatState.LoggedOut
                )
            }
        }

        composeTestRule.onNodeWithText("Logged out").assertIsDisplayed()
    }

    @Test
    fun addMessageTest() {
        composeTestRule.setContent {
            ComposeChatTheme {
                val viewModel = viewModel<ChatViewModel>()
                val state by viewModel.chatState.collectAsStateWithLifecycle()
                ChatScreen(
                    state = state,
                    onCreateNewUserMessage = {
                        viewModel.createNewUserMessage()
                    }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("add new message").performClick()
        composeTestRule.onNodeWithContentDescription("chat preview entry").assertExists()
    }
}