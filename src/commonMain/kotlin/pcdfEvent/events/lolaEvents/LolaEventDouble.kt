package pcdfEvent.events.lolaEvents

import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class LolaEventDouble(
    source: String,
    timestamp: Long,
    stream_name: String,
    val stream_value_double: Double
) : LolaEvent(source, timestamp, stream_name) {
    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            stream_name = "value",
        )
        return pattern
    }

    override fun toString(): String {
        return "Stream Name: $stream_name, Value: $stream_value_double"
    }

    override fun serialize(): String {
        val string = super.serialize()
        return string.replace("stream_name",stream_name).replace("\"value\"", stream_value_double.toString())
    }
}