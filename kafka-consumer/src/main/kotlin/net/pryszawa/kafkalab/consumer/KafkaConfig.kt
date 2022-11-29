package net.pryszawa.kafkalab.consumer

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

@Configuration
class KafkaConfig(

    @Value("\${net.pryszawa.kafka-lab.spring.kafka.bootstrap-servers-config}")
    private val bootstrapServersConfig: String,

) {

    @Bean
    fun getKafkaAdmin(): KafkaAdmin =
        KafkaAdmin(mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
        ))

    @Bean
    fun getKafkaConsumerFactory(): ConsumerFactory<String, String> =
        DefaultKafkaConsumerFactory(mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ))

    @Bean("kafkaListenerContainerFactory")
    fun getKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, String>): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> =
        ConcurrentKafkaListenerContainerFactory<String, String>().also {
            it.consumerFactory = consumerFactory
        }

}
