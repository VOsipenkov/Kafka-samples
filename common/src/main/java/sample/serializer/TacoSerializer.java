package sample.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import sample.model.Taco;

import java.nio.charset.StandardCharsets;

public class TacoSerializer implements Serializer<Taco> {

    @Override
    public byte[] serialize(String s, Taco taco) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(taco).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}