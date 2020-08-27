import org.bialydunajec.gradle.Dependencies
import org.bialydunajec.gradle.Projects
import org.bialydunajec.gradle.TestDependencies
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
    "bootRun"(BootRun::class) {
        enabled = false
    }
    "jar"(Jar::class){
        enabled = true
    }
}

dependencies {
    api(project(Projects.BialyDunajec.DDD.APPLICATION))
    api(project(Projects.BialyDunajec.AcademicMinistry.DOMAIN))
    api(project(Projects.BialyDunajec.AcademicMinistry.MESSAGES))

    compile(Dependencies.Spring.Boot.SPRING_BOOT_STARTER_CACHE)
    compile(Dependencies.Spring.Boot.SPRING_BOOT_STARTER_VALIDATION)

    testCompile(TestDependencies.Spring.Boot.SPRING_BOOT_STARTER_TEST)
}
