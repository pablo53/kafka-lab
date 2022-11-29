package net.pryszawa.kafkalab.producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["net.pryszawa.kafkalab.producer"])
@EnableScheduling
@EnableKafka
class KafkaLabProducerApp

fun main(vararg args: String) {
    runApplication<KafkaLabProducerApp>(*args)
}
