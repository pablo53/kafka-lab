package net.pryszawa.kafkalab.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class KafkaConsumerService {

    companion object {
        private val LOG: Logger = Logger.getLogger(KafkaConsumerService::class.java.canonicalName)
    }

    @KafkaListener(
        topics = ["AdminTopic"],
        groupId = "\${net.pryszawa.kafka-lab.spring.kafka.group-id}",
    )
    fun listenAdminTopic(
        record: ConsumerRecord<String, String>,
        @Payload payload: String,
        @Header(KafkaHeaders.RECEIVED_KEY) key: String?,
        @Header(KafkaHeaders.RECORD_METADATA) metadata: ConsumerRecordMetadata,
    ) {
        LOG.info("Received a message on topic '${metadata?.topic()}', timestamp ${record?.timestamp()}, offset ${metadata?.offset()}, partition ${metadata?.partition()}, leaderEpoch ${record?.leaderEpoch()?.orElse(null)}, key '$key', value '$payload'")
    }

}