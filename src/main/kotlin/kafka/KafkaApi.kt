package com.sudosoo.takeItEasyAdmin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasyAdmin.dto.KafkaGetMemberDetailByMemberIdResponseDto
import com.sudosoo.takeItEasyAdmin.dto.GetMemberRequestDto
import com.sudosoo.takeItEasyAdmin.service.MemberService
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component


@Component
class KafkaApi(
    @Value("\${devsoo.kafka.restapi.topic}")
    val kafkaRestApiTopic: String,
    val kafkaTemplate : KafkaTemplate<String,Any>,
    val objectMapper : ObjectMapper,
    val memberService: MemberService
) {
    fun sendMember(kafkaGetMemberDetailByMemberIdResponseDto: KafkaGetMemberDetailByMemberIdResponseDto) {
        kafkaTemplate.send(kafkaRestApiTopic, kafkaGetMemberDetailByMemberIdResponseDto)
    }

    @SendTo
    @KafkaListener(topics = ["\${devsoo.kafka.restapi.topic}"],groupId = "member-server-consumer-group")
    @Throws(InterruptedException::class)
    fun getMemberRequestDto(message: String): KafkaGetMemberDetailByMemberIdResponseDto? {
        val getMemberRequestDto: GetMemberRequestDto = objectMapper.readValue(message, GetMemberRequestDto::class.java)
        val member = memberService.getInstance(getMemberRequestDto)
        return member.toKafkaResponseDto()
    }

}
