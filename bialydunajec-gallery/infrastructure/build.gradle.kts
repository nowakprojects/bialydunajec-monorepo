version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure")

    compile project(":bialydunajec-gallery:bialydunajec-gallery-domain")
    compile project(":bialydunajec-gallery:bialydunajec-gallery-application")
    compile project(":bialydunajec-gallery:bialydunajec-gallery-presentation")

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
