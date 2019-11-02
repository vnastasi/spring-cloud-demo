package md.vnastasi.cloud

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class TimetableApplication

fun main(args: Array<String>) {
    SpringApplication.run(TimetableApplication::class.java, *args)
}
