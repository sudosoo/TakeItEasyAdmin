package com.sudosoo.takeItEasyAdmin.entity

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.KafkaGetMemberDetailByMemberIdResponseDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Member( var memberName: String , var isDeleted: Boolean = false) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    private constructor(createMemberRequestDto: CreateMemberRequestDto) : this(createMemberRequestDto.memberName)

    companion object {
        fun of(createMemberRequestDto: CreateMemberRequestDto): Member {
            return Member(createMemberRequestDto)
        }
    }

    fun toKafkaResponseDto(): KafkaGetMemberDetailByMemberIdResponseDto? {
        return id?.let { KafkaGetMemberDetailByMemberIdResponseDto(it, memberName) }
    }
    fun disableMember() {
        isDeleted = true
    }
}
