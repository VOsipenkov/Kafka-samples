package sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceProperties {
    @Value("${spring.kafka.consumer.topic}")
    private String orderTopicName;

    @Value("${spring.kafka.consumer.topic-reserve}")
    private String reserveOrderTopicName;

    @Bean
    public String orderTopicName() {
        return orderTopicName;
    }

    @Bean
    public String reserveOrderTopicName() {
        return reserveOrderTopicName;
    }
}