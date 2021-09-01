package pcdfEvent.events

import pcdfEvent.EventType
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

/**
 * A PCDF Event for error messages of every kind.
 * @param message The error message.
 */
class ErrorEvent(
    source: String,
    timestamp: Long,
    val message: String
) : PCDFEvent(source, EventType.ERROR, timestamp) {

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            message = message
        )
        return pattern
    }

    override fun toString(): String {
        return "Message: $message"
    }
}