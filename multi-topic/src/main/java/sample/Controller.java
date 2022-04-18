package sample;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.model.Order;
import sample.model.Taco;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final KafkaTemplate<String, Order> orderKafkaTemplate;
    private final KafkaTemplate<String, Taco> tacoKafkaTemplate;

    @GetMapping(value = "/kafka/order/send")
    public void sendOrder() {
        var order = createOrder();
        orderKafkaTemplate.send("tacocloud.orders.topic", "first", order);
    }

    @GetMapping(value = "/kafka/taco/send")
    public void sendTaco() {
        var taco = createTaco();
        tacoKafkaTemplate.send("tacocloud.tacos.topic", "second", taco);
    }

    private Taco createTaco() {
        var taco = new Taco();
        taco.setId(22L);
        taco.setName("Amazing taco");
        taco.setCreatedAt(LocalDateTime.now());
        return taco;
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
