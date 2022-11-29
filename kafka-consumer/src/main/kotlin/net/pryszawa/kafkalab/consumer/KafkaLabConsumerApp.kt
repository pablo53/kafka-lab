package net.pryszawa.kafkalab.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication(scanBasePackages = ["net.pryszawa.kafkalab.consumer"])
@EnableKafka
class KafkaLabConsumerApp

fun main(vararg args: String) {
    runApplication<KafkaLabConsumerApp>(*args)
}
