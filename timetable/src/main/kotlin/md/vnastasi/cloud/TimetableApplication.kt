package md.vnastasi.cloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class TimetableApplication

fun main(args: Array<String>) {
    val application = SpringApplicationBuilder(TimetableApplication::class.java).build().apply {
        addListeners(ApplicationPidFileWriter())
    }
    application.run(*args)
}
