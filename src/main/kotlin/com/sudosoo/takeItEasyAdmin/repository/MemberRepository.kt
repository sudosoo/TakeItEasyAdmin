package com.sudosoo.takeItEasyAdmin.repository

import com.sudosoo.takeItEasyAdmin.common.repository.BaseRepository
import com.sudosoo.takeItEasyAdmin.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberRepository : BaseRepository<Member, Long> {
    fun existsByMemberName(memberName:String):Boolean

}
