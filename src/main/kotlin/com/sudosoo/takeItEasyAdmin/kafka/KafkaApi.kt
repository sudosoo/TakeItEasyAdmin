package com.sudosoo.takeItEasyAdmin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasyAdmin.dto.KafkaMemberValidateRequestDto
import com.sudosoo.takeItEasyAdmin.service.MemberService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component


@Component
class KafkaApi(
    val kafkaTemplate : KafkaTemplate<String,String>,
    val objectMapper : ObjectMapper,
    val memberService: MemberService
) {

    @KafkaListener(topics = ["\${devsoo.kafka.restapi.request.topic}"], groupId = "member-server-consumer-group")
    @SendTo("\${devsoo.kafka.restapi.reply.topic}")
    fun getMemberRequestDto(@Payload data: String): String {
        val requestDto: KafkaMemberValidateRequestDto = objectMapper.readValue(data, KafkaMemberValidateRequestDto::class.java)
        val member = memberService.getInstance(requestDto)
        return objectMapper.writeValueAsString(member.toKafkaResponseDto())
    }
}
