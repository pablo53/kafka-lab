package net.pryszawa.kafkalab.producer

import net.pryszawa.kafkalab.protobuf.HeartBeatModel
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, ByteArray>,
) {

    companion object {
        private val LOG: Logger = Logger.getLogger(KafkaProducerService::class.java.canonicalName)
    }

    @Scheduled(cron = "\${net.pryszawa.kafkalab.kafka.heart-beat-cron}")
    fun heartBeat() {
        LOG.info("--- Heart Beat ---")
        val heartBeat = HeartBeatModel.HeartBeat.newBuilder()
            .setMsg("--- Heart Beat (protobuf) ---")
            .build()
        kafkaTemplate.send(ProducerRecord(
            "AdminTopic", // topic
            "HeartBeat", // key
            heartBeat.toByteArray(), // payload
        ))
        kafkaTemplate.flush()
    }

}