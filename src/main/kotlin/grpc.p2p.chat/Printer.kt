package grpc.p2p.chat

internal fun prettyPrinter(message: Message) {
    println(message.name + " " + message.time + " " + message.message)
}