package grpc.p2p.chat

import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.io.Closeable
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

class Client(
    private val channel: ManagedChannel,
    private val nameClient: String
) : Closeable {
    private val stub: MessengerGrpcKt.MessengerCoroutineStub = MessengerGrpcKt
        .MessengerCoroutineStub(channel)
    private val dateFormat: DateFormat = DateFormat.getTimeInstance()

    init {
        dateFormat.timeZone = TimeZone.getTimeZone("Moscow")
        getMessages()
    }

    fun sendMessage(messageToSend: String) = runBlocking {
        val requests: Message = Message.newBuilder().apply {
            name = nameClient
            time = dateFormat.format(Date())
            message = messageToSend
        }.build()
        stub.sendMessage(requests)
        prettyPrinter(requests)
    }

    private fun getMessages() {
        timer("getMessages", true, 0, 100) {
            runBlocking {
                val request = GetMessagesRequest.newBuilder().build()
                stub.getMessages(request).collect { message ->
                    prettyPrinter(message)
                }
            }
        }
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}