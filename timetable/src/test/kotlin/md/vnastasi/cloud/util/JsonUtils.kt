package md.vnastasi.cloud.util

internal object JsonUtils {

    fun readString(fileName: String): String {
        val inputStream = javaClass.classLoader.getResourceAsStream("json/$fileName")
        checkNotNull(inputStream) { "Could not read from file </json/$fileName>" }
        return inputStream.bufferedReader().use { it.readText() }
    }
}
