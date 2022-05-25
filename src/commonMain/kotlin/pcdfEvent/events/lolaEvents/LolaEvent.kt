package pcdfEvent.events.lolaEvents

import pcdfEvent.EventType
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

abstract class LolaEvent(
    source: String,
    timestamp: Long,
    val stream_name: String
) : PCDFEvent(source, EventType.LOLA, timestamp) {

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            stream_name = stream_name,
        )
        return pattern
    }

    override fun toString(): String {
        return "Stream Name: $stream_name"
    }
}