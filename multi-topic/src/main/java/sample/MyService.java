package sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import sample.model.Order;
import sample.model.Taco;

@Slf4j
@Service
public class MyService {

    public void listenToOrderTopic(ConsumerRecord<String, Order> orderRecord) {
        log.info("message-order");
        log.info("order {}", orderRecord.value());
    }

    public void listenToTacoTopic(ConsumerRecord<String, Taco> tacoRecord) {
        log.info("message-taco");
        log.info("taco {}", tacoRecord.value());
    }
}
