package org.inbox;

import io.grpc.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InboxClient {

    private final InboxGrpc.InboxBlockingStub blockingStub;

    public InboxClient(Channel channel) {
        blockingStub = InboxGrpc.newBlockingStub(channel);
    }

    public void sendMessage(Message message) {
        SendingResult response;
        try {
            response = blockingStub.sendMessage(message);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: {0}" + e.getStatus());
            return;
        }
    }

    public List<Message> getMessages(String recipient) {
        MessagesResult response;
        try {
            var request = MessagesRequest.newBuilder().setRecipient(recipient).build();
            response = blockingStub.getReceivedMessages(request);
            return response.getMessagesList();

        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: {0}" + e.getStatus());
            return List.of();
        }

    }

    private static void printOptions() {
        System.out.println();
        System.out.println("1) Send a message");
        System.out.println("2) View received messages");
        System.out.println("3) Exit");
        System.out.print("Choose your action: ");
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {

            InboxClient client = new InboxClient(channel);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter you username: ");
            String user = scanner.nextLine();

            while (true) {
                printOptions();
                int action = scanner.nextInt();

                switch (action) {
                    case 1:

                        System.out.print("Enter the recipient's name: ");
                        scanner.nextLine();
                        String recipient = scanner.nextLine();
                        System.out.print("Enter your message: ");
                        String content = scanner.nextLine();
                        var message = Message.newBuilder()
                                .setAuthor(user)
                                .setContent(content)
                                .setRecipient(recipient)
                                .setTimestamp(Instant.now().toEpochMilli()).build();
                        client.sendMessage(message);

                        System.out.println("Message sent successfully.");
                        break;

                    case 2:
                        var list = client.getMessages(user);
                        if (list.isEmpty()) {
                            System.out.println("No messages received so far.");
                        } else {

                            for (Message msg : list) {
                                var time = formatter.format(Instant.ofEpochMilli(msg.getTimestamp()));
                                System.out.print("From: " + msg.getAuthor()
                                        + "    " + "To: " + msg.getRecipient()
                                        + "    Sent: " + time + "\n");
                                System.out.println(msg.getContent());
                                System.out.println();
                            }

                        }
                        break;
                    default:
                        System.exit(0);
                        break;
                }

            }

        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
