package net.pryszawa.kafkalab.producer

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfig(

    @Value("\${spring.kafka.bootstrap-servers-config}")
    private val bootstrapServersConfig: String,

) {

    @Bean
    fun getKafkaAdmin(): KafkaAdmin =
        KafkaAdmin(mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
        ))

    @Bean
    fun adminTopic(): NewTopic =
        NewTopic("AdminTopic", 10, 3)

    @Bean
    fun getKafkaProducerFactory(): ProducerFactory<String, String> =
        DefaultKafkaProducerFactory(mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ))

    @Bean
    fun getKafkaTemplate(kafkaProducerFactory: ProducerFactory<String, String>): KafkaTemplate<String, String> =
        KafkaTemplate(kafkaProducerFactory)

}
