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
	implementation("io.vavr:vavr:0.10.3")
	implementation("com.querydsl:querydsl-jpa:4.4.0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("com.jcabi:jcabi-ssh:1.6.1")

	implementation("io.arrow-kt:arrow-core:0.11.0")
	implementation("io.arrow-kt:arrow-syntax:0.11.0")
	kapt("io.arrow-kt:arrow-meta:0.11.0")
	kapt("com.querydsl:querydsl-apt:4.4.0:jpa")
	kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final")

	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")

	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude("org.junit.vintage:junit-vintage-engine")
	}
}

plugins {
	id("org.springframework.boot") version "2.3.2.RELEASE"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.4.20"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.4.20"
	kotlin("kapt") version "1.4.20"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

/*kapt {
	generateStubs = true
}

sourceSets {
	main {
		java {
			srcDirs.add(file("$buildDir/generated/source/kapt/main"))
		}
	}
}*/

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()