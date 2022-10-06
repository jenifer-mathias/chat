package br.com.jms.chat.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import br.com.jms.chat.MainViewModel
import br.com.jms.chat.R
import br.com.jms.chat.data.exampleUiState
import br.com.jms.chat.theme.ChatTheme
import java.util.Calendar
import java.util.Date
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ConversationFragment : Fragment() {

    private val activityViewModel: MainViewModel by activityViewModels()

    private var webSocket: WebSocket? = null
    private val client by lazy { OkHttpClient() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher
            ) {
                ChatTheme {
                    ConversationContent(
                        uiState = exampleUiState,
                        navigateToProfile = { user ->
                            // Click callback
                            val bundle = bundleOf("userId" to user)
                            findNavController().navigate(
                                R.id.nav_profile,
                                bundle
                            )
                        },
                        onMessageEnter = ::onMessageEnter,
                        onNavIconPressed = {
                            activityViewModel.openDrawer()
                        },
                        // Add padding so that we are inset from any navigation bars
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets
                                .navigationBars
                                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                        )
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        start()
    }

    private fun start() {
        val request: Request =
            Request.Builder().url("ws://10.0.2.2:32092/").build()
        val listener = object: WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val currentTime: Date = Calendar.getInstance().time
                exampleUiState.addMessage(
                    // mudar para pegar o autor dinamicamente
                    Message("Giulia", text, currentTime.toString())
                )
            }
        }
        webSocket = client.newWebSocket(request, listener)
    }

    private fun onMessageEnter(message: Message) {
        webSocket?.send(message.content)
    }

    override fun onStop() {
        super.onStop()
        webSocket?.close(1000, "Goodbye !")
    }
    override fun onDestroy() {
        super.onDestroy()
        client.dispatcher.executorService.shutdown()
    }
}
