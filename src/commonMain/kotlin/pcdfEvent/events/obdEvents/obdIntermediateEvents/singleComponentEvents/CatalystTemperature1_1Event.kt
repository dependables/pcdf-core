package pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents

import pcdfEvent.events.obdEvents.OBDCommand
import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class CatalystTemperature1_1Event(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var temperature: Double
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, -1.0) {
        temperature = (data.toInt(16).div(10.0) - 40.0)
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            catalyst_temperature_1_1 = temperature
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Cat. Temp. 1 1: $temperature ${
            OBDCommand.CATALYST_TEMPERATURE_1_1.unit
        }"
    }
}