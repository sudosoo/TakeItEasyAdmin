package com.sudosoo.takeItEasyAdmin.service

import com.sudosoo.takeItEasyAdmin.common.service.JpaService
import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.KafkaMemberValidateRequestDto
import com.sudosoo.takeItEasyAdmin.entity.Member
import com.sudosoo.takeItEasyAdmin.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
@Transactional
class MemberServiceImpl (
    val memberRepository : MemberRepository ): MemberService , JpaService<Member,Long> {

    override var jpaRepository: JpaRepository<Member, Long> = memberRepository

    override fun create(createMemberRequestDto: CreateMemberRequestDto): Member {
        if (memberRepository.existsByMemberName(createMemberRequestDto.memberName)) {
            throw IllegalArgumentException("이미 존재하는 회원 입니다")
        }
        return saveModel(Member.of(createMemberRequestDto))
    }

    override fun getInstance(requestDto: KafkaMemberValidateRequestDto) : Member {
        return memberRepository.findById(requestDto.memberId).orElseThrow{ NoSuchElementException(" 멤버를 찾을 수 없습니다.") }
        }

    override fun disableByMemberId(memberId: Long) {
        val member = memberRepository.findById(memberId).orElseThrow {NoSuchElementException("멤버를 찾을 수 없습니다.") }
        member.disableMember()
        memberRepository.save(member)
    }
}

