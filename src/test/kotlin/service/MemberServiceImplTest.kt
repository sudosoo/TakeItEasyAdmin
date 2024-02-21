package service

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.GetMemberRequestDto
import com.sudosoo.takeItEasyAdmin.entity.Member
import com.sudosoo.takeItEasyAdmin.kafka.KafkaApi
import com.sudosoo.takeItEasyAdmin.repository.MemberRepository
import com.sudosoo.takeItEasyAdmin.service.MemberService
import com.sudosoo.takeItEasyAdmin.service.MemberServiceImpl
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class MemberServiceImplTest {
    private val memberRepository: MemberRepository = mockk<MemberRepository>()
    private val kafkaApi: KafkaApi = mockk<KafkaApi>()
    private val memberService: MemberService = MemberServiceImpl(memberRepository, kafkaApi)

    private val createMemberRequestDto = CreateMemberRequestDto("testMember")
    private val member = Member.of(createMemberRequestDto)

    @Test
    @DisplayName("createMember")
    fun create() {
        //given
        every { memberRepository.existByMemberName(any()) } returns false
        every { memberRepository.save(member) } returns member

        //when
        val result = memberService.create(createMemberRequestDto)

        //then
        val expectedMemberName = createMemberRequestDto.memberName
        val actualMemberName = result.memberName

        assertNotNull(result, "The actual member should not be null")
        assertEquals(expectedMemberName, actualMemberName, "Expected Member Name: $expectedMemberName, Actual Member Name: $actualMemberName")
        verify(exactly = 1) { memberRepository.save(any()) }
    }

    @Test
    @DisplayName("getMember")
    fun getInstance() {
        //given
        val requestDto = GetMemberRequestDto(1L)
        every { memberRepository.findById(requestDto.memberId) } returns Optional.of(member)
        every { kafkaApi.sendMember(member) } just Runs

        //when
        val result = memberService.getInstance(requestDto)

        //then
        assertNotNull(result)
        verify(exactly = 1) { memberRepository.findById(1L) }
        verify(exactly = 1) { kafkaApi.sendMember(member) }
    }

    @Test
    fun disableByMemberId() {
        //given
        val requestMemberId = 1L
        every { memberRepository.findById(requestMemberId) } returns Optional.of(member)
        every { memberRepository.save(member) } returns member

        //when
        memberService.disableByMemberId(requestMemberId)

        //then
        assertTrue(member.isDeleted)
        verify(exactly = 1) { memberRepository.findById(requestMemberId) }
        verify(exactly = 1) { memberRepository.save(member) }
    }
}
