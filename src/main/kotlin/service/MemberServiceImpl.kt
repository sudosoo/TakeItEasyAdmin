package com.sudosoo.takeItEasyAdmin.service

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.repository.MemberRepository
import com.sudosoo.takeItEasyAdmin.entity.Member
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberServiceImpl(private val memberRepository : MemberRepository) : MemberService  {

    override fun createMember(requestDto : CreateMemberRequestDto){
        val member = Member.of(requestDto)
        memberRepository.save(member)
    }

    override fun getMemberByMemberId(memberId: Long): Member {
        return memberRepository.findById(memberId).orElseThrow { NoSuchElementException("멤버를 찾을 수 없습니다.") }
    }

    override fun getMemberByMemberName(memberName: String): Member {
        return memberRepository.findByMemberName(memberName).orElseThrow{ NoSuchElementException("멤버를 찾을 수 없습니다.")}
    }

    override fun findAllMembers(): List<Member> {
        return memberRepository.findAll()
    }

    override fun disableByMemberId(memberId: Long) {
        var member = memberRepository.findById(memberId).orElseThrow {NoSuchElementException("멤버를 찾을 수 없습니다.") }
        member.disableMember()
    }

}

