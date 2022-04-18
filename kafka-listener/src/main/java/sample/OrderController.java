package sample;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.model.Order;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final KafkaTemplate kafkaTemplate;
    private final String orderTopic;
    private final ApplicationContext applicationContext;

    @GetMapping(value = "/kafka/order/send")
    public void get() {
        var order = createOrder();
        kafkaTemplate.send("tacocloud.orders.topic", "second", order);
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
