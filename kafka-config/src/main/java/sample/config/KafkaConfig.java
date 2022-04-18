package sample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import sample.model.Taco;
import sample.service.MyService;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {
    private final MyService myService;

    //producer
    @Bean
    @ConfigurationProperties("spring.kafka.producer")
    public Properties producerProperties() {
        return new Properties();
    }

    @Bean
    public ProducerFactory<String, Taco> producerFactory(Properties producerProperties) {
        Map<String, Object> config = new HashMap<>();
        producerProperties.stringPropertyNames().forEach(p -> config.put(p, producerProperties.get(p)));
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Taco> kafkaTemplate(ProducerFactory<String, Taco> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    //consumer
    @Bean
    @ConfigurationProperties("spring.kafka.consumer")
    public Properties consumerProperties() {
        return new Properties();
    }

    @Bean
    public ConsumerFactory<String, Taco> consumerFactory(Properties consumerProperties) {
        Map<String, Object> config = new HashMap<>();
        consumerProperties.stringPropertyNames().forEach(p -> config.put(p, consumerProperties.get(p)));
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public MessageListener<String, Taco> messageListener() {
        return message -> myService.doSomething(message);
    }

    @Bean
    public ConcurrentMessageListenerContainer messageListenerContainer(
        Properties consumerProperties, ConsumerFactory<String, Taco> consumerFactory) {
        var containerProperties = new ContainerProperties(new String[]{(String) consumerProperties.get("topic")});
        containerProperties.setMessageListener(messageListener());
        containerProperties.setGroupId("first");
        return new ConcurrentMessageListenerContainer(consumerFactory, containerProperties);
    }
}
