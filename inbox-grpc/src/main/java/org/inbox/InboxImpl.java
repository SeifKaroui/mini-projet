package org.inbox;

import io.grpc.stub.StreamObserver;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class InboxImpl extends InboxGrpc.InboxImplBase {
    private HashMap<String, ArrayList<Message>> messages;

    public InboxImpl() {
        this.messages = new HashMap<String, ArrayList<Message>>();
    }

    @Override
    public void sendMessage(Message request, StreamObserver<SendingResult> responseObserver) {
        var recipient = request.getRecipient();
        var entry = messages.computeIfAbsent(recipient, k -> new ArrayList<Message>());
        entry.add(request);

        var sendingResult = SendingResult.newBuilder().build();

        responseObserver.onNext(sendingResult);
        responseObserver.onCompleted();
    }

    @Override
    public void getReceivedMessages(MessagesRequest request, StreamObserver<MessagesResult> responseObserver) {
        var recipient = request.getRecipient();
        var list = new ArrayList<Message>();
        if(messages.containsKey(recipient)){
            list.addAll(messages.get(recipient));
        }
        var result = MessagesResult.newBuilder().addAllMessages(list).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
