package com.sudosoo.takeItEasyAdmin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasyAdmin.com.sudosoo.takeItEasyAdmin.dto.RequestMessage
import com.sudosoo.takeItEasyAdmin.entity.Member
import com.sudosoo.takeItEasyAdmin.service.MemberService
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class KafkaApi(
    @Value("\${devsoo.kafka.restapi.topic}")
    val kafkaRestApiTopic: String,
    val kafkaTemplate : KafkaTemplate<String,Any>,
    val objectMapper : ObjectMapper,
    val memberService: MemberService
) {
    fun sendMember(producerMember : Member) {
        kafkaTemplate.send(kafkaRestApiTopic, producerMember)
    }

    @KafkaListener(topics = ["\${devsoo.kafka.restapi.topic}"], groupId = "member-consumer")
    fun getMemberRequestDto(message: String) {

        val requestMessage: RequestMessage = objectMapper.readValue(message, RequestMessage::class.java)

        when (requestMessage.targetMethod) {
            "getInstance" -> {
                val currentTimeMillis = System.currentTimeMillis()
                println("Current time in milliseconds: $currentTimeMillis")
            }
            else -> {

            }
        }
    }


}
