package com.sudosoo.takeItEasyAdmin.service

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.KafkaMemberValidateRequestDto
import com.sudosoo.takeItEasyAdmin.entity.Member

interface MemberService {
    fun create(createMemberRequestDto: CreateMemberRequestDto): Member
    fun getInstance(requestDto: KafkaMemberValidateRequestDto): Member
    fun disableByMemberId(memberId: Long)
}

