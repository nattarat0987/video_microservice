package com.microemail.email.Config;

import com.microemail.common.Emailcommon.EmailRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String server;
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> map = new HashMap<>();
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        //   String ==> String
        //   KEY_DESERIALIZER_CLASS_CONFIG
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class); // Key String
        //   Json => Object
        //   VALUE_DESERIALIZER_CLASS_CONFIG
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_DOC, JsonSerializer.class);// Key Object
        map.put(ConsumerConfig.GROUP_ID_CONFIG,"json");
        return map;
    }

    @Bean
    public ConsumerFactory<String, EmailRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs()
                ,new StringDeserializer(),
                new JsonDeserializer<>(EmailRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,EmailRequest>   kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, EmailRequest> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        return containerFactory;

    }
}
