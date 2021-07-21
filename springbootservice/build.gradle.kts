import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven{setUrl("https://dl.bintray.com/lorenwang/maven")}
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    //注解使用
    implementation("org.springframework.boot:spring-boot-starter-data-rest")


    // JPA Data (We are going to use Repositories, Entities, Hibernate, etc...)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("mysql:mysql-connector-java")

    //swagger注释
    implementation("io.springfox:springfox-swagger2:2.6.0")
    implementation("io.springfox:springfox-swagger-ui:2.6.0")
    //java工具库
    implementation("com.github.loren-wang:JavaCustomToolsFromLorenWang:1.0.44")
    //java数据格式化库
    implementation("com.github.loren-wang:JavaDataParseFromLorenWang:1.0.11")
    implementation("com.google.code.gson:gson:2.8.5")

    //日志打印框架
    implementation("org.slf4j:slf4j-api:1.7.7")
    implementation("ch.qos.logback:logback-core:1.1.3")
    implementation("ch.qos.logback:logback-access:1.1.3")
    implementation("ch.qos.logback:logback-classic:1.1.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
