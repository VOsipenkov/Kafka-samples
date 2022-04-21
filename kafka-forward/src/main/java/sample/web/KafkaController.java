package sample.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.model.Order;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaTemplate kafkaTemplate;
    private final String orderTopicName;

    @GetMapping(value = "/kafka/order/send")
    public ResponseEntity<String> sendOrderToReserve() {
        kafkaTemplate.send(orderTopicName, "first", createOrder());
        return new ResponseEntity<>("message sent", HttpStatus.OK);
    }

    private Order createOrder() {
        Order order = new Order();
        order.setId(11L);
        order.setPlacedAt(LocalDateTime.now());
        order.setDeliveryName("deliveryName");
        order.setDeliveryCity("Moscow");
        order.setDeliveryStreet("Pushkinskaya");
        order.setDeliveryState("abc");
        order.setDeliveryZip("123");
        order.setCcNumber("CcNumber");
        order.setCcExpiration("CcExpiration");
        order.setCcCvv("333");
        return order;
    }
}
