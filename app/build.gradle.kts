plugins {
    kotlin("jvm") version "1.8.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")

    implementation("org.liquibase:liquibase-core:4.22.0")
    // Makes liquibase log to Slf4j
    runtimeOnly("com.mattbertolini:liquibase-slf4j:5.0.0")

    implementation("org.mybatis:mybatis:3.5.13")

    // DB connection pool
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.1.214")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("de.eekboom.kotlin_mybatis.MainKt")
}

sourceSets.main {
    // Add kotlin source directory to resource folder, so that MyBatis mapper files can live
    // close to the mapper interface and data classes
    resources {
        srcDirs(project.sourceSets.main.get().kotlin.srcDirs)
        exclude("/**/*.kt" )
    }
}
