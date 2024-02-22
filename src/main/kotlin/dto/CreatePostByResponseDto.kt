package com.sudosoo.takeItEasyAdmin.dto

class CreatePostByResponseDto(var memberId: Long, var memberName: String, val targetMethod: String = "createPost") {
    constructor(memberId: Long, memberName: String) : this(memberId, memberName, "createPost")
}

