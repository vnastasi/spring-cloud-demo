import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun timestamp(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
