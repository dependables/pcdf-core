package pcdfEvent.events.lolaEvents

import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class LolaEventBool(
    source: String,
    timestamp: Long,
    stream_name: String,
    val stream_value_bool: Boolean
) : LolaEvent(source, timestamp, stream_name) {
    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            stream_name = stream_name,
            stream_value_bool = stream_value_bool
        )
        return pattern
    }

    override fun toString(): String {
        return "Stream Name: $stream_name, Value: $stream_value_bool"
    }
}