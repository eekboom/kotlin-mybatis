package de.eekboom.kotlin_mybatis

import org.apache.ibatis.annotations.AutomapConstructor

data class AppUser @AutomapConstructor constructor(
    var id: Int,
    var name: String,
    var email: String,
    var roles: MutableList<Role>
)
