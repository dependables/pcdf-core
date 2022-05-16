package pcdfEvent.events

import pcdfEvent.EventType
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class LolaEvent(
    source: String,
    timestamp: Long,
    private val stream_name: String,
    private val stream_value: Double
) : PCDFEvent(source, EventType.ERROR, timestamp) {

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            stream_name = stream_name,
            stream_value = stream_value
        )
        return pattern
    }

    override fun toString(): String {
        return "Stream Name: $stream_name, Value: $stream_value"
    }
}