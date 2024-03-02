package com.sudosoo.takeItEasyAdmin.repository

import com.sudosoo.takeItEasyAdmin.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberRepository : JpaRepository<Member,Long> {
    fun findByMemberName(memberName:String): Optional<Member>
    fun existsByMemberName(memberName:String):Boolean

}
