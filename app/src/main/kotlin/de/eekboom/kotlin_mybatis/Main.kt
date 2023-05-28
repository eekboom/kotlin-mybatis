package de.eekboom.kotlin_mybatis

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

private val logger = getLogger {}

fun main() {
    val dataSource: DataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = "jdbc:h2:~/kotlin_mybatis"
            username = "sa"
            password = ""
            driverClassName = "org.h2.Driver"
        }
    )

    migrateDb(dataSource)

    val sqlSessionFactory = sqlSessionFactory(dataSource)

    sqlSessionFactory.openSession().use {
        val mapper: Mapper = it.getMapper(Mapper::class.java)

        logger.info("Querying roles")
        val roles = mapper.selectAllRoles()
        logger.info("Roles $roles")

        logger.info("Querying users")
        val users = mapper.selectAllUsers()
        logger.info("Users $users")
    }

}
