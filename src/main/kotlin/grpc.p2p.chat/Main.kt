package grpc.p2p.chat

import io.grpc.ManagedChannelBuilder

fun main() {
    val startType: StartType = getStartType()
    println("Enter your name")
    val name: String = readLine() ?: "null"

    when (startType) {
        StartType.SERVER -> {
            val port = getPort()
            val server = Server(port, name)
            server.start()
            while (true) {
                val msg: String = readLine() ?: "null"
                if (msg == "STOP SERVER") break
                server.send(msg)
            }
            server.close()
        }
        StartType.CLIENT -> {
            val host = getHost()
            val port = getPort()
            val client = Client(
                ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext().build(),
                name
            )
            while (true) {
                val msg = readLine() ?: "null"
                if (msg == "CLOSE CLIENT") break
                client.sendMessage(msg)
            }
            client.close()
        }
    }
}

internal fun getStartType(): StartType {
    println("Select a start type:\n	Press 1 to start as a server\n	Press 2 to start as a client")
    do {
        val startTypeInput = readLine()
        when (startTypeInput?.toIntOrNull()) {
            1 -> return StartType.SERVER
            2 -> return StartType.CLIENT
            else -> {
            }
        }
        println(
            "Incorrect start type!\n" +
                    "Select a start type:\n	press 1 to start as a server\n	press 2 to start as a client"
        )
    } while (true)
}

internal fun getPort(): Int {
    val maxPortNumber = 65535
    println("Enter the port of server")
    do {
        val port = readLine()?.toIntOrNull()
        if (port != null && port > 0 && port < maxPortNumber) return port
        println("Port is incorrect!\nEnter the port of server")
    } while (true)
}

internal fun getHost(): String {
    println("Enter the ip of server")
    return readLine() ?: "127.0.0.1"
}