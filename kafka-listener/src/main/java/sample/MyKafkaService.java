package sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sample.model.Order;

@Slf4j
@Service
@EnableKafka
public class MyKafkaService {

    @KafkaListener(topics = "tacocloud.orders.topic", groupId = "first")
    public void kafkaListener1(Order order) {
        int i = 1;
        log.info("message-{}", i);
        log.info("Order {}", order);
    }

    @KafkaListener(topics = "#{orderTopic}", groupId = "second")
    public void kafkaListener2(Order order) {
        int i = 2;
        log.info("message-{}", i);
        log.info("Order {}", order);
    }
}
