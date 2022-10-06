package br.com.jms.chat.data

import br.com.jms.chat.R
import br.com.jms.chat.conversation.ConversationUiState
import br.com.jms.chat.conversation.Message
import br.com.jms.chat.profile.ProfileScreenState

private val initialMessages = emptyList<Message>()
val exampleUiState = ConversationUiState(
    initialMessages = initialMessages,
    channelName = "#aula de redes",
    channelMembers = 42
)

/**
 * Example "me" profile.
 */
val meProfile = ProfileScreenState(
    userId = "Giulia",
    photo = R.drawable.giulia,
    name = "Giulia",
    status = "Online",
    displayName = "giulia_barbieri",
    position = "QA Pleno",
    timeZone = "12:25 AM local time (Eastern Daylight Time)",
    commonChannels = "2"
)

/**
 * Example colleague profile
 */
val colleagueProfile = ProfileScreenState(
    userId = "Jenifer",
    photo = R.drawable.jenifer,
    name = "Jenifer",
    status = "Online",
    displayName = "jenifer_mathias",
    position = "Android Dev. Pleno\n",
    timeZone = "2:25 AM local time (Eastern Daylight Time)",
    commonChannels = null
)
