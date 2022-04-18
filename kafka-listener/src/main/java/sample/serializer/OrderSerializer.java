package sample.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Serializer;
import sample.model.Order;

import java.nio.charset.StandardCharsets;

public class OrderSerializer implements Serializer<Order> {

    @Override
    public byte[] serialize(String s, Order order) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(order).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
