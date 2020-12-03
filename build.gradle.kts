import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
		maven { setUrl("https://kotlin.bintray.com/kotlinx") }
		jcenter()
	}
}

repositories {
	maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
	maven { setUrl("https://kotlin.bintray.com/kotlinx") }
	jcenter()
}

dependencies {
	implementation("com.querydsl:querydsl-jpa:4.4.0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("io.arrow-kt:arrow-core:0.11.0")
	implementation("io.arrow-kt:arrow-syntax:0.11.0")
	kapt("io.arrow-kt:arrow-meta:0.11.0")

	kaptTest("io.arrow-kt:arrow-meta:0.11.0")
	kaptTest("com.querydsl:querydsl-apt:4.4.0:jpa")
	kaptTest("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

	testImplementation("com.h2database:h2:1.4.197")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa:2.3.2.RELEASE")
	testImplementation("org.springframework.boot:spring-boot-starter:2.3.2.RELEASE")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.2.RELEASE") {
		exclude("org.junit.vintage:junit-vintage-engine")
	}
}

plugins {
	id("org.jetbrains.kotlin.plugin.allopen") version "1.4.20"
	kotlin("jvm") version "1.4.20"
	kotlin("kapt") version "1.4.20"
}

allOpen {
	annotation("org.springframework.transaction.annotation.Transactional")
	annotation("org.springframework.boot.test.context.TestConfiguration")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()