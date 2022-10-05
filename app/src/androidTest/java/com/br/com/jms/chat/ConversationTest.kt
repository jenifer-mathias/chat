package com.br.com.jms.chat

import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.jms.chat.R
import br.com.jms.chat.conversation.ConversationContent
import br.com.jms.chat.conversation.ConversationTestTag
import br.com.jms.chat.conversation.ConversationUiState
import br.com.jms.chat.conversation.LocalBackPressedDispatcher
import br.com.jms.chat.data.exampleUiState
import br.com.jms.chat.theme.ChatTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Checks that the features in the Conversation screen work as expected.
 */
class ConversationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val themeIsDark = MutableStateFlow(false)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Before
    fun setUp() {
        // Launch the conversation screen
        composeTestRule.setContent {
            val onBackPressedDispatcher = composeTestRule.activity.onBackPressedDispatcher
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides onBackPressedDispatcher,
            ) {
                ChatTheme(isDarkTheme = themeIsDark.collectAsStateWithLifecycle(false).value) {
                    ConversationContent(
                        uiState = conversationTestUiState,
                        navigateToProfile = { },
                        onNavIconPressed = { }
                    )
                }
            }
        }
    }

    @Test
    fun app_launches() {
        // Check that the conversation screen is visible on launch
        composeTestRule.onNodeWithTag(ConversationTestTag).assertIsDisplayed()
    }

    @Test
    fun userScrollsUp_jumpToBottomAppears() {
        // Check list is snapped to bottom and swipe up
        findJumpToBottom().assertDoesNotExist()
        composeTestRule.onNodeWithTag(ConversationTestTag).performTouchInput {
            this.swipe(
                start = this.center,
                end = Offset(this.center.x, this.center.y + 500),
                durationMillis = 200
            )
        }
        // Check that the jump to bottom button is shown
        findJumpToBottom().assertIsDisplayed()
    }

    @Test
    fun jumpToBottom_snapsToBottomAndDisappears() {
        // When the scroll is not snapped to the bottom
        composeTestRule.onNodeWithTag(ConversationTestTag).performTouchInput {
            this.swipe(
                start = this.center,
                end = Offset(this.center.x, this.center.y + 500),
                durationMillis = 200
            )
        }
        // Snap scroll to the bottom
        findJumpToBottom().performClick()

        // Check that the button is hidden
        findJumpToBottom().assertDoesNotExist()
    }

    @Test
    fun jumpToBottom_snapsToBottomAfterUserInteracted() {
        // First swipe
        composeTestRule.onNodeWithTag(
            testTag = ConversationTestTag,
            useUnmergedTree = true // https://issuetracker.google.com/issues/184825850
        ).performTouchInput {
            this.swipe(
                start = this.center,
                end = Offset(this.center.x, this.center.y + 500),
                durationMillis = 200
            )
        }
        // Second, snap to bottom
        findJumpToBottom().performClick()

        // Open Emoji selector
        openEmojiSelector()

        // Assert that the list is still snapped to bottom
        findJumpToBottom().assertDoesNotExist()
    }

    @Test
    fun changeTheme_scrollIsPersisted() {
        // Swipe to show the jump to bottom button
        composeTestRule.onNodeWithTag(ConversationTestTag).performTouchInput {
            this.swipe(
                start = this.center,
                end = Offset(this.center.x, this.center.y + 500),
                durationMillis = 200
            )
        }

        // Check that the jump to bottom button is shown
        findJumpToBottom().assertIsDisplayed()

        // Set theme to dark
        themeIsDark.value = true

        // Check that the jump to bottom button is still shown
        findJumpToBottom().assertIsDisplayed()
    }

    private fun findJumpToBottom() =
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.jumpBottom))

    private fun openEmojiSelector() =
        composeTestRule
            .onNodeWithContentDescription(
                label = composeTestRule.activity.getString(R.string.emoji_selector_bt_desc),
                useUnmergedTree = true // https://issuetracker.google.com/issues/184825850
            )
            .performClick()
}

/**
 * Make the list of messages longer so the test makes sense on tablets.
 */
private val conversationTestUiState = ConversationUiState(
    initialMessages = (exampleUiState.messages.plus(exampleUiState.messages)),
    channelName = "#aula de redes",
    channelMembers = 42
)
