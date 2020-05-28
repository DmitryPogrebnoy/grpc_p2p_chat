## Simple peer-to-peer chat on gRPC

### Prerequisites
 - Kotlin 1.3
 - Java 11
 - Gradle 5

### How run
Clone the repository:
``
git clone https://github.com/DmitryPogrebnoy/grpc_p2p_chat
``

Navigate to project folder:
``
cd grpc_p2p_chat
``

Run gradle jar: ``gradle build``

Navigate to jar's folder: ``cd build\libs``

Run chat: ``java -jar grpc_p2p_chat.jar``

Start one instance in server mode by specifying an arbitrary name 
and an arbitrary port. 
Start one instance in client mode by specifying the name,
 server ip (127.0.0.1) and server port to which the client will connect.

Now you can write messages from the server and from the client and observe them at the client and server, respectively. 

### Test run
Build with ``docker build -t grpc_p2p_chat:1.0 .``

Then run docker container as a server 
``docker run -it -p<server_port>:<host_port> grpc_p2p_chat:1.0``

Select server mode, specify a name and ``<server_port>`` as a port. 
Done - the server is turned on.

Run another docker container as a client
``docker run -it grpc_p2p_chat:1.0``

Select client mode, specify a name,
set server ip (where server ip is the ip of your host from `` sudo ip addr show docker0``)
and set the port (where port is `` <host_port> ``). 

That's all!