package sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.model.Taco;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
    private final KafkaTemplate kafkaTemplate;

    @GetMapping(value = "/kafka/taco/send")
    public void get() {
        var taco = createTaco();
        kafkaTemplate.send("tacocloud.tacos.topic", "first", taco);
        log.info("send taco to topic {}", "tacocloud.tacos.topic");
    }

    private Taco createTaco() {
        var taco = new Taco();
        taco.setId(22L);
        taco.setName("Amazing taco");
        taco.setCreatedAt(LocalDateTime.now());
        return taco;
    }
}
