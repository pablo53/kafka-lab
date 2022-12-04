package net.pryszawa.kafkalab.producer

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

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
    fun adminTopic(): NewTopic =
        NewTopic("AdminTopic", 10, 3)

    @Bean
    fun getKafkaProducerFactory(): ProducerFactory<String, String> =
        DefaultKafkaProducerFactory(mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServersConfig,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ) + sslProperties)

    @Bean
    fun getKafkaTemplate(kafkaProducerFactory: ProducerFactory<String, String>): KafkaTemplate<String, String> =
        KafkaTemplate(kafkaProducerFactory)

}
