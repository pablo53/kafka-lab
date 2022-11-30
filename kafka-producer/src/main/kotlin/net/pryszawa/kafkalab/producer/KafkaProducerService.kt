package net.pryszawa.kafkalab.producer

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {

    companion object {
        private val LOG: Logger = Logger.getLogger(KafkaProducerService::class.java.canonicalName)
    }

    @Scheduled(cron = "\${spring.kafka.heart-beat-cron}")
    fun heartBeat() {
        LOG.info("--- Heart Beat ---")
        kafkaTemplate.send(ProducerRecord(
            "AdminTopic", // topic
            "HeartBeat", // key
            "--- Heart Beat ---", // payload
        ))
    }

}