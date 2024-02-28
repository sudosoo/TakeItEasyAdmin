package com.sudosoo.takeItEasyAdmin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasyAdmin.dto.KafkaMemberValidateRequestDto
import com.sudosoo.takeItEasyAdmin.service.MemberService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component


@Component
class KafkaApi(
    @Value("\${devsoo.kafka.restapi.topic}")
    val kafkaRestApiTopic: String,
    val kafkaTemplate : KafkaTemplate<String,String>,
    val objectMapper : ObjectMapper,
    val memberService: MemberService,
    val log : Logger = LogManager.getLogger()
) {

    @KafkaListener(topics = ["\${devsoo.kafka.restapi.topic}"], groupId = "member-server-consumer-group")
    @SendTo("replyingTopic")
    fun getMemberRequestDto(@Payload data: String): String {
        val requestDto: KafkaMemberValidateRequestDto = objectMapper.readValue(data, KafkaMemberValidateRequestDto::class.java)
        val member = memberService.getInstance(requestDto)
        return objectMapper.writeValueAsString(member.toKafkaResponseDto())
    }
}
