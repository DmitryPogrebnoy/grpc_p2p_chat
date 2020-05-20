##Simple peer-to-peer chat on gRPC

###Prerequisites
 - Kotlin 1.3
 - Java 11

###How run
Clone the repository:
``
git clone https://github.com/DmitryPogrebnoy/grpc_p2p_chat
``

Navigate to project folder:
``
cd grpc_p2p_chat
``

Run gradle jar: ``gradlew jar``

Navigate to jar's folder: ``cd build\libs``

Run chat: ``java -jar grpc_p2p_chat.jar``

Start one instance in server mode by specifying an arbitrary name 
and an arbitrary port. 
Start one instance in client mode by specifying the name,
 server ip (127.0.0.1) and server port to which the client will connect.

Now you can write messages from the server and from the client and observe them at the client and server, respectively. 
