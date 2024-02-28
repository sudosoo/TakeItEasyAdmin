package com.sudosoo.takeItEasyAdmin.controller

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.dto.KafkaMemberValidateRequestDto
import com.sudosoo.takeItEasyAdmin.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberController (private val memberService : MemberService) {
    @PostMapping(value = ["/create"], name = "createMember")
    fun createMember(@Validated @RequestBody requestDto : CreateMemberRequestDto) : ResponseEntity<Void> {
        memberService.create(requestDto)
        return ResponseEntity.ok().build()
    }
    @GetMapping(value = ["/get"], name = "getMember")
    fun getMember(@RequestBody requestDto : KafkaMemberValidateRequestDto) {
        memberService.getInstance(requestDto)
    }

    @PatchMapping(value = ["/disable"], name = "disableMember")
    fun disableMember(@RequestParam memberId : Long) {
        memberService.disableByMemberId(memberId)
    }




}

