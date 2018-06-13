package com.example.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import com.example.config.ConsumerConfiguration;

@SpringBootApplication
@Import(ConsumerConfiguration.class)
public class MessageConsumerApplication {

    @Autowired
    private IntegrationFlowContext flowContext;

    @Autowired
    private KafkaProperties kafkaProperties;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(MessageConsumerApplication.class).run(args);
        List<String> validTopics = Arrays.asList("ERROR", "WARNING", "INFORMATION");
        List<String> topics = new ArrayList<>();
        if (args.length > 0) {
            for (String arg : args) {
                if (validTopics.contains(arg)) {
                    topics.add(arg);
                }
            }
        }
        context.getBean(MessageConsumerApplication.class).run(context, topics);
        context.close();
    }

    private void run(ConfigurableApplicationContext context, List<String> topics) {

        PollableChannel consumerChannel = context.getBean("consumerChannel", PollableChannel.class);
        for (String topic : topics) {
            addAnotherListenerForTopics(topic);
        }
        Message<?> received = consumerChannel.receive();
        while (received != null) {
            System.out.println("Received " + received.getPayload());
            received = consumerChannel.receive();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
	private void addAnotherListenerForTopics(String... topics) {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
		IntegrationFlow flow = IntegrationFlows
                .from(Kafka.messageDrivenChannelAdapter(
                        new DefaultKafkaConsumerFactory(consumerProperties), topics))
                .channel("consumerChannel").get();
        this.flowContext.registration(flow).register();
    }
}
