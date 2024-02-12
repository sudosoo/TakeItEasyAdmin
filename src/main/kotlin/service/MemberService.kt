package com.sudosoo.takeItEasyAdmin.service

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.entity.Member

interface MemberService {
    fun createMember(createMemberRequestDto: CreateMemberRequestDto)
    fun getMemberByMemberId(memberId: Long): Member
    fun getMemberByMemberName(memberName: String): Member
    fun findAllMembers(): List<Member>
    fun disableByMemberId(memberId: Long)
}

