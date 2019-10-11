import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
}

dependencies {
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-application"))
    compile(project(":bialydunajec-registrations:bialydunajec-registrations-messages"))

    compile("org.springframework.boot:spring-boot-starter-data-mongodb")
    testCompile("org.springframework.boot:spring-boot-starter-test")

    compile("org.springframework.boot:spring-boot-starter-webflux")
}