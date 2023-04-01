package com.youtubeclonebynattarat.nattaratprojects.Configurations;

import com.microemail.common.Emailcommon.EmailRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class Kafkaconfig {

    @Value("${server.kafka.producer.bootstrap-servers}")
    private String server;

    @Bean
    public Map<String, Object> porducerConfigs() {
        Map<String, Object> map = new HashMap<>();
        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        //    String ==> String
        //   KEY_DESERIALIZER_CLASS_CONFIG
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Key String
        //    Object => Json
        //   VALUE_DESERIALIZER_CLASS_CONFIG
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);// Key Object
        return map;
    }

    @Bean
    public KafkaTemplate<String, EmailRequest> KafkaEmailTemplate() {
        DefaultKafkaProducerFactory<String, EmailRequest> ProducerFactory
                = new DefaultKafkaProducerFactory<>(porducerConfigs());
        return new KafkaTemplate<>(ProducerFactory);

    }

}
