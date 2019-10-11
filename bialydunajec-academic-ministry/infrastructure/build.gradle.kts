import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
}


dependencies {
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure"))

    compile(project(":bialydunajec-academic-ministry:bialydunajec-academic-ministry-domain"))
    compile(project(":bialydunajec-academic-ministry:bialydunajec-academic-ministry-application"))
    compile(project(":bialydunajec-academic-ministry:bialydunajec-academic-ministry-presentation"))

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}