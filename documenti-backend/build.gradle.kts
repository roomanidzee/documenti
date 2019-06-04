import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

project.extra.set("versions", mapOf(
        "mybatis" to "2.0.1",
        "mapstruct" to "1.3.0.Final",
        "postgresql" to "42.2.5",
        "liquibase" to "3.6.3",
        "jackson" to "2.9.9",
        "kotlin" to "1.2.71",
        "logback" to "1.2.3",
        "slf4j" to "1.7.26",
        "pdf-builder" to "1.3.0",
        "jjwt" to "0.9.1"
))

plugins {
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"

    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    kotlin("kapt") version "1.2.71"
}

val versions : Map<String, String> by project.extra

group = "com.romanidze"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven(url = "http://repo.spring.io/plugins-release/")
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${versions["jackson"]}")

    implementation("org.jetbrains.kotlin:kotlin-reflect:${versions["kotlin"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions["kotlin"]}")

    implementation("org.liquibase:liquibase-core:${versions["liquibase"]}")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:${versions["mybatis"]}")

    runtimeOnly("org.postgresql:postgresql:${versions["postgresql"]}")

    compile("org.mapstruct:mapstruct:${versions["mapstruct"]}")
    kapt("org.mapstruct:mapstruct-processor:${versions["mapstruct"]}")

    compile("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    compile("ch.qos.logback:logback-classic:${versions["logback"]}")
    compile("ch.qos.logback:logback-access:${versions["logback"]}")

    compile("org.slf4j:slf4j-api:${versions["slf4j"]}")
    compile("org.slf4j:jul-to-slf4j:${versions["slf4j"]}")
    compile("org.slf4j:log4j-over-slf4j:${versions["slf4j"]}")

    compile("com.github.timrs2998:pdf-builder:${versions["pdf-builder"]}")
    compile("io.jsonwebtoken:jjwt:${versions["jjwt"]}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
