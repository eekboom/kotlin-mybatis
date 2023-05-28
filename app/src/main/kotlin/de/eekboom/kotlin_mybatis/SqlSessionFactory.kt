package de.eekboom.kotlin_mybatis

import org.apache.ibatis.logging.LogFactory
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import javax.sql.DataSource

private val logger = getLogger {}

fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory {
    logger.info("Creating mybatis sql session factory")
    LogFactory.useLog4J2Logging()
    val transactionFactory: TransactionFactory = JdbcTransactionFactory()
    val environment = Environment("development", transactionFactory, dataSource)
    val configuration = Configuration(environment).apply {
        isMapUnderscoreToCamelCase = true
        addMapper(Mapper::class.java)
    }

    return SqlSessionFactoryBuilder().build(configuration)
}