package grpc.p2p.chat

import io.grpc.Server
import io.grpc.ServerBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking
import java.io.Closeable
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Server constructor(
    private val port: Int,
    private val name: String
) : Closeable {
    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(MessengerService())
        .build()
    private val dateFormat: DateFormat = DateFormat.getTimeInstance()
    private val flow: Channel<Message> = Channel<Message>()

    init {
        dateFormat.timeZone = TimeZone.getTimeZone("Moscow")
    }

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@Server.close()
                println("*** server shut down")
            }
        )
    }

    override fun close() {
        server.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }

    fun send(msg: String) {
        val message: Message = Message.newBuilder()
            .setName(name)
            .setTime(dateFormat.format(Date()))
            .setMessage(msg)
            .build()
        runBlocking {
            flow.send(message)
        }
        prettyPrinter(message)
    }

    private inner class MessengerService : MessengerGrpcKt.MessengerCoroutineImplBase() {
        override fun getMessages(request: GetMessagesRequest): Flow<Message> {
            return flow.consumeAsFlow()
        }

        override suspend fun sendMessage(request: Message): SendMessageReply {
            prettyPrinter(request)
            return SendMessageReply.newBuilder().build()
        }
    }
}