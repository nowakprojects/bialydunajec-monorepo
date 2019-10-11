import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
}


dependencies {
	compile(project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure"))

	compile(project(":bialydunajec-registrations:bialydunajec-registrations-domain"))
	compile(project(":bialydunajec-registrations:bialydunajec-registrations-application"))
	compile(project(":bialydunajec-registrations:bialydunajec-registrations-presentation"))

	compile("org.springframework.boot:spring-boot-starter-security")
	compile("org.springframework.boot:spring-boot-starter-data-jpa")
	runtime("com.h2database:h2")
	runtime("mysql:mysql-connector-java")
	testCompile("org.springframework.boot:spring-boot-starter-test")
}
