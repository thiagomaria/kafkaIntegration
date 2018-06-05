package com.example.message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.message.Message.MessageType;

@Component
public class MessageCreator {

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        //creating warning messages
        messages.add(new Message(1L, "Warning 1!", MessageType.WARNING));
        messages.add(new Message(2L, "Warning 2!", MessageType.WARNING));
        messages.add(new Message(3L, "Warning 3!", MessageType.WARNING));
        messages.add(new Message(4L, "Warning 4!", MessageType.WARNING));
        messages.add(new Message(5L, "Warning 5!", MessageType.WARNING));
        //creating error messages
        messages.add(new Message(6L, "Error 1!", MessageType.ERROR));
        messages.add(new Message(7L, "Error 2!", MessageType.ERROR));
        messages.add(new Message(8L, "Error 3!", MessageType.ERROR));
        //creating information messages
        messages.add(new Message(9L, "Information 1!", MessageType.INFORMATION));
        messages.add(new Message(10L, "Information 2!", MessageType.INFORMATION));
        messages.add(new Message(11L, "Information 3!", MessageType.INFORMATION));
        messages.add(new Message(12L, "Information 4!", MessageType.INFORMATION));

        return messages;
    }
}
