package net.pryszawa.kafkalab.producer

import net.pryszawa.kafkalab.avro.HeartBeat2
import net.pryszawa.kafkalab.protobuf.HeartBeatModel
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.nio.ByteBuffer
import java.util.logging.Logger

@Service
class KafkaProducerService(
    private val kafkaByteArrayTemplate: KafkaTemplate<String, ByteArray>,
    private val kafkaByteBufferTemplate: KafkaTemplate<String, ByteBuffer>,
) {

    companion object {
        private val LOG: Logger = Logger.getLogger(KafkaProducerService::class.java.canonicalName)
    }

    @Scheduled(cron = "\${net.pryszawa.kafkalab.kafka.heart-beat-cron}")
    fun heartBeat() {
        LOG.info("--- Heart Beat ---")

        // protobuf:
        val heartBeat = HeartBeatModel.HeartBeat.newBuilder()
            .setMsg("--- Heart Beat (protobuf) @${System.currentTimeMillis()} ---")
            .build()
        kafkaByteArrayTemplate.send(ProducerRecord(
            "AdminTopic", // topic
            "HeartBeat", // key
            heartBeat.toByteArray(), // payload
        ))

        // avro:
        val heartBeat2 = HeartBeat2.newBuilder()
            .setMsg("--- Heart Beat (avro) @${System.currentTimeMillis()} ---")
            .build()
        kafkaByteBufferTemplate.send(ProducerRecord(
            "AdminTopic", // topic
            "HeartBeat2", // key
            heartBeat2.toByteBuffer(), // payload
        ))

        kafkaByteArrayTemplate.flush()
    }

}