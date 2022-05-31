package pcdfEvent

import pcdfPattern.PCDFPattern
import pcdfPattern.PatternParser
import serialization.Serializer

/**
 * Objects of this class represent a PCDF object.
 * Can be generated from a [PCDFPattern] and to a PCDFPattern.
 * @param source UUID or ECU-ID of the event's source.
 * @param type The event's [EventType].
 * @param timestamp Timestamp of the event's generation (commonly nano-seconds).
 */
open class PCDFEvent(
    val source: String,
    val type: EventType,
    var timestamp: Long,
) {
    /**
     * Returns the corresponding PCDFPattern.
     */
    open fun getPattern(): PCDFPattern {
        return PCDFPattern(source, type.toString(), timestamp, null)
    }

    open fun toIntermediate() : PCDFEvent {
        return this
    }

    open fun serialize() : String {
        return serializer.generateFromPattern(this.getPattern())
    }

    companion object {
        private val serializer = Serializer()

        /**
         * Parses a PCDF JSON-String into the corresponding [PCDFEvent].
         * @param string JSON-String to be parsed.
         */
        fun fromString(string : String) : PCDFEvent {
            return serializer.parseToPattern(string).toEvent()
        }
    }
}
