package net.pryszawa.kafkalab.producer

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

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

}
