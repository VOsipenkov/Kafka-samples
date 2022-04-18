package sample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import sample.MyService;
import sample.model.Order;
import sample.model.Taco;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {
    private final MyService myService;

    //Producers
    @Bean
    @ConfigurationProperties("spring.kafka.producer.order")
    public Properties orderProducerProperties() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties("spring.kafka.producer.taco")
    public Properties tacoProducerProperties() {
        return new Properties();
    }

    @Bean
    public ProducerFactory<String, Order> orderProducerFactory(Properties orderProducerProperties) {
        Map<String, Object> config = new HashMap<>();
        orderProducerProperties.stringPropertyNames().forEach(p -> config.put(p, orderProducerProperties.get(p)));
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ProducerFactory<String, Taco> tacoProducerFactory(Properties tacoProducerProperties) {
        Map<String, Object> config = new HashMap<>();
        tacoProducerProperties.stringPropertyNames().forEach(p -> config.put(p, tacoProducerProperties.get(p)));
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Order> orderKafkaTemplate(ProducerFactory<String, Order> orderProducerFactory) {
        return new KafkaTemplate<>(orderProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, Taco> tacoKafkaTemplate(ProducerFactory<String, Taco> tacoProducerFactory) {
        return new KafkaTemplate<>(tacoProducerFactory);
    }

    // Consumers
    @Bean
    @ConfigurationProperties("spring.kafka.consumer.order")
    public Properties orderConsumerProps() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties("spring.kafka.consumer.taco")
    public Properties tacoConsumerProps() {
        return new Properties();
    }

    @Bean
    public ConsumerFactory<String, Order> orderConsumerFactory(Properties orderConsumerProps) {
        Map<String, Object> config = new HashMap<>();
        orderConsumerProps.stringPropertyNames().forEach(p -> config.put(p, orderConsumerProps.get(p)));
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConsumerFactory<String, Taco> tacoConsumerFactory(Properties tacoConsumerProps) {
        Map<String, Object> config = new HashMap<>();
        tacoConsumerProps.stringPropertyNames().forEach(p -> config.put(p, tacoConsumerProps.get(p)));
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public MessageListener<String, Order> orderListener() {
        return orderRecord -> myService.listenToOrderTopic(orderRecord);
    }

    @Bean
    public MessageListener<String, Taco> tacoListener() {
        return tacoRecord -> myService.listenToTacoTopic(tacoRecord);
    }

    @Bean
    public ConcurrentMessageListenerContainer orderMessageListenerContainer(
        Properties orderConsumerProps, ConsumerFactory<String, Order> orderConsumerFactory) {
        var containerProperties = new ContainerProperties(new String[]{(String) orderConsumerProps.get("topic")});
        containerProperties.setMessageListener(orderListener());
        containerProperties.setGroupId("first");
        return new ConcurrentMessageListenerContainer(orderConsumerFactory, containerProperties);
    }

    @Bean
    public KafkaMessageListenerContainer<String, Taco> tacoMessageListenerContainer(
        Properties tacoConsumerProps, ConsumerFactory<String, Taco> tacoConsumerFactory) {
        var containerProperties = new ContainerProperties((String) tacoConsumerProps.get("topic"));
        containerProperties.setGroupId("taco-id");
        containerProperties.setMessageListener(tacoListener());
        return new KafkaMessageListenerContainer<>(tacoConsumerFactory, containerProperties);
    }
}
