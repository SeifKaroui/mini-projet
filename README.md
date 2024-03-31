# mini-projet
All projects were built using Maven.
## How to run
### Todo app with java rmi
```bash
cd todo-rmi
mvn compile
mvn exec:java -Dexec.mainClass="org.todo.Server"
mvn exec:java -Dexec.mainClass="org.todo.Client"
```

### Inbox app with grpc
```bash
cd inbox-grpc
mvn compile
mvn exec:java -Dexec.mainClass="org.inbox.InboxServer"
mvn exec:java -Dexec.mainClass="org.inbox.InboxClient"
```

### Chat app with sockets
```bash
cd chat-sockets
mvn compile
mvn exec:java -Dexec.mainClass="org.chat.ChatServer"
mvn exec:java -Dexec.mainClass="org.chat.ChatClient"
```
