plugins {
    kotlin("jvm") version "1.3.50"
}

allprojects {
    apply plugin: 'kotlin'
    apply plugin: 'kotlin-spring'
    apply plugin: 'kotlin-jpa'
    apply plugin: 'kotlin-kapt'
    apply plugin: 'groovy'
    apply plugin: 'maven'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'
    apply plugin: 'maven-publish'

    group = 'org.bialydunajec'
    sourceCompatibility = 1.8

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = ["-Xjsr305=strict"]
            jvmTarget = "1.8"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = ["-Xjsr305=strict"]
            jvmTarget = "1.8"
        }
    }

    test {
        useJUnitPlatform {
            includeEngines 'spek2', 'junit-jupiter', 'junit-vintage'
        }
    }

    repositories {
        mavenCentral()
        maven { url "https://repo.spring.io/snapshot" }
        maven {
            url "${nexusReleaseUrl}"
            credentials {
                username nexusUser
                password nexusPassword
            }
        }
        maven {
            url "${nexusSnapshotUrl}"
            credentials {
                username nexusUser
                password nexusPassword
            }
        }
        maven { url "https://dl.bintray.com/arrow-kt/arrow-kt/" }
        maven { url "https://dl.bintray.com/spekframework/spek" }
    }

    uploadArchives {
        repositories {
            mavenDeployer {
                repository(url: "$nexusReleaseUrl") {
                    authentication(userName: nexusUser, password: nexusPassword)
                }
                snapshotRepository(url: "$nexusSnapshotUrl") {
                    authentication(userName: nexusUser, password: nexusPassword)
                }
            }
        }
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/nowakprojects/bialydunajec-kotlin-backend")
                credentials {
                    username = project.findProperty("gpr.user") ?: System.getenv("GPR_USER")
                    password = project.findProperty("gpr.key") ?: System.getenv("GPR_API_KEY")
                }
            }
        }
        publications {
            gpr(MavenPublication) {
                from(components.java)
            }
        }
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        compile("org.jetbrains.kotlin:kotlin-reflect")

        //JAVA 9 integration
        compile("javax.xml.bind:jaxb-api:${jaxbApiVersion}")
        compile('javax.activation:activation:1.1')
        compile('org.glassfish.jaxb:jaxb-runtime:2.3.0')

        //Kotlin Arrow
        compile "io.arrow-kt:arrow-core:$arrowVersion"
        compile "io.arrow-kt:arrow-syntax:$arrowVersion"
        compile "io.arrow-kt:arrow-typeclasses:$arrowVersion"
        compile "io.arrow-kt:arrow-data:$arrowVersion"
        compile "io.arrow-kt:arrow-instances-core:$arrowVersion"
        compile "io.arrow-kt:arrow-instances-data:$arrowVersion"
        kapt "io.arrow-kt:arrow-annotations-processor:$arrowVersion"
        compile "io.arrow-kt:arrow-query-language:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-free:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-instances-free:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-mtl:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-instances:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-rx2:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-rx2-instances:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-reactor:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-reactor-instances:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-kotlinx-coroutines:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-effects-kotlinx-coroutines-instances:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-optics:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-generic:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-recursion:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-instances-recursion:$arrowVersion" //optional
        compile "io.arrow-kt:arrow-integration-retrofit-adapter:$arrowVersion" //optional

        compile("com.github.nowakprojects:kt-time-traveler-core:${ktTimeTravelerVersion}")

        testCompile("org.jetbrains.kotlin:kotlin-test")
        testCompile("org.spockframework:spock-core:${spockVersion}")
        testCompile("org.spockframework:spock-spring:${spockVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-api:${jUnitVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-params:${jUnitVersion}")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:${jUnitVersion}")
        testCompile("org.assertj:assertj-core:${assertjVersion}")
        testCompile "org.spekframework.spek2:spek-dsl-jvm:$spekVersion"
        testRuntime "org.spekframework.spek2:spek-runner-junit5:$spekVersion"
        testCompile "com.willowtreeapps.assertk:assertk-jvm:$assertkVersion"
        testCompile("com.github.nowakprojects:kt-time-traveler-test:${ktTimeTravelerVersion}")
        testCompile "com.tngtech.archunit:archunit-junit5-api:${archUnitVersion}"
        testRuntime "com.tngtech.archunit:archunit-junit5-engine:${archUnitVersion}"

    }

}

version = '0.0.2'

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-noarg:${kotlinVersion}")
    }
}

dependencies {
    compile project(':bialydunajec-news:bialydunajec-news-presentation')
    compile project(':bialydunajec-news:bialydunajec-news-infrastructure')

    compile project(':bialydunajec-camp-edition:bialydunajec-camp-edition-presentation')
    compile project(':bialydunajec-camp-edition:bialydunajec-camp-edition-infrastructure')

    compile project(':bialydunajec-academic-ministry:bialydunajec-academic-ministry-presentation')
    compile project(':bialydunajec-academic-ministry:bialydunajec-academic-ministry-infrastructure')

    compile project(':bialydunajec-registrations:bialydunajec-registrations-presentation')
    compile project(':bialydunajec-registrations:bialydunajec-registrations-infrastructure')
    compile project(':bialydunajec-registrations:bialydunajec-registrations-read-model')

    compile project(':bialydunajec-users:bialydunajec-users-presentation')
    compile project(':bialydunajec-users:bialydunajec-users-infrastructure')

    compile project(':bialydunajec-email:bialydunajec-email-presentation')
    compile project(':bialydunajec-email:bialydunajec-email-infrastructure')
    compile project(':bialydunajec-email:bialydunajec-email-read-model')

    compile project(':bialydunajec-authorization:bialydunajec-authorization-server')

    //compile project(':bialydunajec-camp-schedule:bialydunajec-camp-schedule-presentation')
    //compile project(':bialydunajec-camp-schedule:bialydunajec-camp-schedule-infrastructure')
    //compile project(':bialydunajec-camp-schedule:bialydunajec-camp-schedule-read-model')

    //compile project(':bialydunajec-faq:bialydunajec-faq-presentation')
    //compile project(':bialydunajec-faq:bialydunajec-faq-infrastructure')

    //compile project(':bialydunajec-gallery:bialydunajec-gallery-presentation')
    //compile project(':bialydunajec-gallery:bialydunajec-gallery-infrastructure')

    compile('org.springframework.boot:spring-boot-starter-data-jpa')
    compile('org.springframework.boot:spring-boot-starter-hateoas')
    compile('org.springframework.boot:spring-boot-starter-mail')
    compile('org.springframework.boot:spring-boot-starter-quartz')
    compile('org.springframework.boot:spring-boot-starter-webflux')
    compile('com.fasterxml.jackson.module:jackson-module-kotlin')
    compile "com.github.ulisesbocchio:jasypt-spring-boot-starter:${jasyptVersion}"


    //Swagger 2 - REST Api documentation
    compile("io.springfox:springfox-swagger2:${swaggerVersion}")
    compile("io.springfox:springfox-swagger-ui:${swaggerVersion}")

    //Fake data generator
    compile("com.devskiller:jfairy:${jfairyVersion}")

    runtime('com.h2database:h2')
    runtime("mysql:mysql-connector-java:${mysqlConnectorVersion}")
    runtime("org.postgresql:postgresql:${postgresqlConnectorVersion}")


    compileOnly('org.springframework.boot:spring-boot-configuration-processor')
    testCompile('org.springframework.boot:spring-boot-starter-test')
    testCompile('io.projectreactor:reactor-test')

    if (!project.hasProperty("release")) {
        runtime('de.flapdoodle.embed:de.flapdoodle.embed.mongo')
    }
}