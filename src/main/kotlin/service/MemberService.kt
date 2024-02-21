package com.sudosoo.takeItEasyAdmin.service

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.GetMemberRequestDto
import com.sudosoo.takeItEasyAdmin.entity.Member

interface MemberService {
    fun create(createMemberRequestDto: CreateMemberRequestDto): Member
    fun getInstance(requestDto: GetMemberRequestDto): Member
    fun disableByMemberId(memberId: Long)

//    fun getMemberByMemberName(memberName: String): Member
//    fun findAllMembers(): List<Member>
}

