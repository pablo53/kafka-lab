package net.pryszawa.kafkalab.consumer

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.InitializingBean
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

    @Value("\${net.pryszawa.kafkalab.kafka.bootstrap-servers-config}")
    private val bootstrapServersConfig: String,

    @Value("\${net.pryszawa.kafkalab.kafka.truststore.location}")
    private val truststoreLocation: String,

    @Value("\${net.pryszawa.kafkalab.kafka.truststore.password}")
    private val truststorePassword: String,

) : InitializingBean {

    private lateinit var sslProperties: Map<String, String>

    override fun afterPropertiesSet() {
        sslProperties = mapOf(
            "security.protocol" to "SSL",
            "ssl.protocol" to "TLSv1.2",
            "ssl.truststore.location" to truststoreLocation,
            "ssl.truststore.password" to truststorePassword,
            "ssl.endpoint.identification.algorithm" to "", // this is to avoid dns names check in SSL
        )
    }

    @Bean
    fun getKafkaAdmin(): KafkaAdmin =
        KafkaAdmin(mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
        ) + sslProperties)

    @Bean
    fun getKafkaConsumerFactory(): ConsumerFactory<String, String> =
        DefaultKafkaConsumerFactory(mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ) + sslProperties)

    @Bean("kafkaListenerContainerFactory")
    fun getKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, String>): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> =
        ConcurrentKafkaListenerContainerFactory<String, String>().also {
            it.consumerFactory = consumerFactory
        }

}
