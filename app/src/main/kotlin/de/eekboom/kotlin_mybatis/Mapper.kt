package de.eekboom.kotlin_mybatis

interface Mapper {
    fun selectAllUsers(): List<AppUser>

    fun selectAllRoles(): List<Role>
}
