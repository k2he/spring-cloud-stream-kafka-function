package com.cloudstreamkafkafunctional.programming.producerooc.config;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

//@Configuration
@Slf4j
public class StreamConfigDisabled {

//    @Bean
//    public Supplier<Message<MyEvent>> eventProducer() {
//        return () -> {
//            var randomNum = new Random().nextInt(Department.values().length);
//            var payload = MyEvent.builder()
//                .name("Name" + randomNum)
//                .department(Department.values()[randomNum])
//                .build();
//
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            log.info("creating an event {} to publish to Kafka. Date:{}", payload, new Date());
//            return MessageBuilder
//                .withPayload(payload)
//                .setHeader(KafkaHeaders.MESSAGE_KEY, payload.getDepartment().name())
//                .build();
//        };
//    }

}
