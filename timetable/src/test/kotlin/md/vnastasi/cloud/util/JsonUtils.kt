package md.vnastasi.cloud.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object JsonUtils {

    fun readString(fileName: String): String {
        val inputStream = javaClass.classLoader.getResourceAsStream("json/$fileName")
        checkNotNull(inputStream) { "Could not read from file </json/$fileName>" }
        return inputStream.bufferedReader().use { it.readText() }
    }

    inline fun <reified T> readObject(fileName: String): T {
        val jsonContents = readString(fileName)
        val objectMapper = ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper.readValue(jsonContents)
    }
}
