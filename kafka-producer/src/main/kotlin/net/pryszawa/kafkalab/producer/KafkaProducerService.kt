package net.pryszawa.kafkalab.producer

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class KafkaProducerService {

    companion object {
        private val LOG: Logger = Logger.getLogger(KafkaProducerService::class.java.canonicalName)
    }

    @Scheduled(cron = "\${spring.kafka.heart-beat-cron}")
    fun heartBeat() {
        LOG.info("--- Heart Beat ---")
    }

}