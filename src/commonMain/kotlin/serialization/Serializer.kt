package serialization

import pcdfPattern.PCDFPattern
import kotlinx.serialization.json.Json

/**
 * Main class to convert [PCDFPattern] into JSON strings and vice versa.
 */
class Serializer {

    val serializer = Json {
        encodeDefaults = false
    }

    /**
     * Serializes a [PCDFPattern] into a JSON string.
     * @param pattern Pattern to be serialized.
     */
    fun generateFromPattern(pattern: PCDFPattern): String {
        return serializer.encodeToString(PCDFPattern.serializer(), pattern)
    }

    /**
     * Parses a JSON string into a [PCDFPattern].
     * @param input JSON string to be parsed.
     */
    fun parseToPattern(input: String): PCDFPattern {
        return serializer.decodeFromJsonElement(
            PCDFPattern.serializer(),
            serializer.parseToJsonElement(input)
        )
    }
}
