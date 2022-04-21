package sample.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import sample.model.Order;

@Slf4j
@Service
@EnableKafka
public class ReplyService {

    @KafkaListener(topics = "#{orderTopicName}", groupId = "first")
    @SendTo(value = "#{reserveOrderTopicName}")
    public Order doReply(ConsumerRecord<String, Order> record) {
        log.info("Received order {}", record.value());
        return record.value();
    }
}
