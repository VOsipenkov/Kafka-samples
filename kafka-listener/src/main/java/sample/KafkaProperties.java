package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProperties {

    @Value("${spring.kafka.consumer.topic}")
    private String topic;

    @Bean
    public String orderTopic() {
        return topic;
    }
}
