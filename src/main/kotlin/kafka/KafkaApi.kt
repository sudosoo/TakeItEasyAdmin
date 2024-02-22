package com.sudosoo.takeItEasyAdmin.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.sudosoo.takeItEasyAdmin.dto.CreatePostByResponseDto
import com.sudosoo.takeItEasyAdmin.dto.GetMemberRequestDto
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
    fun sendMember(createPostByResponseDto: CreatePostByResponseDto) {
        kafkaTemplate.send(kafkaRestApiTopic, createPostByResponseDto)
    }

    @KafkaListener(topics = ["\${devsoo.kafka.restapi.topic}"], groupId = "member-consumer")
    fun getMemberRequestDto(message: String) {

        val getMemberRequestDto: GetMemberRequestDto = objectMapper.readValue(message, GetMemberRequestDto::class.java)

        when (getMemberRequestDto.targetMethod) {
            "validateMemberId" -> {
                val member = memberService.getInstance(getMemberRequestDto)
                val responseDto = member.id?.let { CreatePostByResponseDto(it,member.memberName) }
                    ?: throw IllegalStateException("Failed to create responseDto")
                sendMember(responseDto)
            }
            else -> {
                throw IllegalStateException("target is null")
            }
        }
    }


}
