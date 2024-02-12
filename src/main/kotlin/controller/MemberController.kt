package com.sudosoo.takeItEasyAdmin.controller

import com.sudosoo.takeItEasyAdmin.dto.CreateMemberRequestDto
import com.sudosoo.takeItEasyAdmin.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController (private val memberService : MemberService) {
    @PostMapping(value = ["/create"], name = "createMember")
    fun createMember(@Validated @RequestBody requestDto : CreateMemberRequestDto) : ResponseEntity<Void> {
        memberService.createMember(requestDto)
        return ResponseEntity.ok().build()
    }

}

