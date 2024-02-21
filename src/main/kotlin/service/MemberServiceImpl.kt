package com.sudosoo.takeItEasyAdmin.service

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.GetMemberRequestDto
import com.sudosoo.takeItEasyAdmin.entity.Member
import com.sudosoo.takeItEasyAdmin.kafka.KafkaApi
import com.sudosoo.takeItEasyAdmin.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
@Transactional
class MemberServiceImpl (
    private val memberRepository : MemberRepository,
    private val kafkaApi: KafkaApi
):MemberService {

    override fun create(createMemberRequestDto: CreateMemberRequestDto): Member {
        if (memberRepository.existByMemberName(createMemberRequestDto.memberName)) {
            throw IllegalArgumentException("이미 존재하는 회원 입니다")
        }
        return memberRepository.save(Member.of(createMemberRequestDto))
    }

    override fun getInstance(getMemberRequestDto: GetMemberRequestDto) : Member {
        val member = memberRepository.findById(getMemberRequestDto.memberId).orElseThrow{NoSuchElementException("멤버를 찾을 수 없습니다.") }
        kafkaApi.sendMember(member)
        return member
        }

    override fun disableByMemberId(memberId: Long) {
        val member = memberRepository.findById(memberId).orElseThrow {NoSuchElementException("멤버를 찾을 수 없습니다.") }
        member.disableMember()
        memberRepository.save(member)
    }

//
//    override fun getMemberByMemberName(memberName: String): Member {
//        return memberRepository.findByMemberName(memberName).orElseThrow{ NoSuchElementException("멤버를 찾을 수 없습니다.")}
//    }
//
//    override fun findAllMembers(): List<Member> {
//        return memberRepository.findAll()
//    }
//

}

