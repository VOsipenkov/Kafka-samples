Kafka listener is example of use autoconfigured kafkaTemplate(using application.yml)
and couple @KafkaListener annotations, who catches all messages from topic There are serializer\deserializer because used Object structure instead simple
String's in topic