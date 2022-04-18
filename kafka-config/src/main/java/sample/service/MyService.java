package sample.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import sample.model.Taco;

@Slf4j
@Service
public class MyService {
    public void doSomething(ConsumerRecord<String, Taco> consumerRecord) {
        log.info("doSomething called");
        log.info("taco {}", consumerRecord.value());
    }
}
