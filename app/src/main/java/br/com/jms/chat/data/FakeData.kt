package br.com.jms.chat.data

import br.com.jms.chat.R
import br.com.jms.chat.conversation.ConversationUiState
import br.com.jms.chat.conversation.Message
import br.com.jms.chat.profile.ProfileScreenState

private val initialMessages = emptyList<Message>()
    //listOf(
//    Message(
//        "me",
//        "Check it out!",
//        "8:07 PM"
//    ),
//    Message(
//        "me",
//        "Thank you!",
//        "8:06 PM",
//        R.drawable.sticker
//    ),
//    Message(
//        "Taylor Brooks",
//        "You can use all the same stuff",
//        "8:05 PM"
//    ),
//    Message(
//        "Taylor Brooks",
//        "@aliconors Take a look at the `Flow.collectAsState()` APIs",
//        "8:05 PM"
//    ),
//    Message(
//        "John Glenn",
//        "Compose newbie as well, have you looked at the JetNews sample? Most blog posts end up " +
//                "out of date pretty fast but this sample is always up to date and deals with async " +
//                "data loading (it's faked but the same idea applies) \uD83D\uDC49" +
//                "https://github.com/android/compose-samples/tree/master/JetNews",
//        "8:04 PM"
//    ),
//    Message(
//        "me",
//        "Compose newbie: I’ve scourged the internet for tutorials about async data loading " +
//                "but haven’t found any good ones. What’s the recommended way to load async " +
//                "data and emit composable widgets?",
//        "8:03 PM"
//    )
//)

val exampleUiState = ConversationUiState(
    initialMessages = initialMessages,
    channelName = "#aula de redes",
    channelMembers = 42
)

/**
 * Example colleague profile
 */
val colleagueProfile = ProfileScreenState(
    userId = "12345",
    photo = R.drawable.giulia,
    name = "Giulia",
    status = "Online",
    displayName = "giulia_barbieri",
    position = "Senior Android Dev at Openlane",
    timeZone = "12:25 AM local time (Eastern Daylight Time)",
    commonChannels = "2"
)

/**
 * Example "me" profile.
 */
val meProfile = ProfileScreenState(
    userId = "me",
    photo = R.drawable.jenifer,
    name = "Jenifer",
    status = "Online",
    displayName = "jenifer_mathias",
    position = "Android Dev. Pleno\n",
    timeZone = "2:25 AM local time (Eastern Daylight Time)",
    commonChannels = null
)
