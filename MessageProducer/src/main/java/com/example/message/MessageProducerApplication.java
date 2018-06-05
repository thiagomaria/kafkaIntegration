package com.example.message;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.example.config.ProducerConfiguration;

@SpringBootApplication
@Import(ProducerConfiguration.class)
public class MessageProducerApplication {

    @Autowired
    private MessageCreator messageCreator;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(MessageProducerApplication.class).run(args);
        context.getBean(MessageProducerApplication.class).run(context);
        context.close();
    }

    private void run(ConfigurableApplicationContext context) {
        MessageChannel producerChannel = context.getBean("producerChannel", MessageChannel.class);

        List<Message> messages = messageCreator.getMessages();
        for (Message message : messages) {
            Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, message.getType().toString());
            producerChannel.send(new GenericMessage<String>(message.toString(), headers));
        }
        System.out.println("Producer finished...");
    }
}
