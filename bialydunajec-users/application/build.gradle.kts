version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-application")
    compile project(":bialydunajec-users:bialydunajec-users-domain")
    compile project(":bialydunajec-users:bialydunajec-users-messages")

    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-validation")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
