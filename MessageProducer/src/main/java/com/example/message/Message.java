package com.example.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;

@Getter
@Setter
@ToString
@AllArgsConstructor(access=AccessLevel.PUBLIC)
public class Message {

    public enum MessageType {
        ERROR, WARNING, INFORMATION;
    }

    private long messageId;
    private String content;
    private MessageType type;
}
