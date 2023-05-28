package de.eekboom.kotlin_mybatis

import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Scope
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.visitor.ChangeExecListener
import liquibase.command.CommandScope
import liquibase.command.core.UpdateCommandStep
import liquibase.command.core.helpers.ChangeExecListenerCommandStep
import liquibase.command.core.helpers.DatabaseChangelogCommandStep
import liquibase.command.core.helpers.DbUrlConnectionCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import java.io.OutputStream
import javax.sql.DataSource

private val logger = getLogger {}

fun migrateDb(dataSource: DataSource) {
    logger.info("Running liquibase update")

    dataSource.connection.use { connection ->
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))
        val changeLogFile = "/liquibase-changelog.xml"

        val scopeObjects = mapOf(
            Scope.Attr.database.name to database,
            Scope.Attr.resourceAccessor.name to ClassLoaderResourceAccessor()
        )

        Scope.child(scopeObjects) {
            val updateCommand = CommandScope(*UpdateCommandStep.COMMAND_NAME).apply {
                addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database)
                addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changeLogFile)
                addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, Contexts().toString())
                addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, LabelExpression().originalString)
                addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, null as ChangeExecListener?)
                addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, ChangeLogParameters(database))

                setOutput(OutputStream.nullOutputStream()) // Suppress output to stdout (logging will still occur)
            }
            updateCommand.execute()
        }
    }
}
