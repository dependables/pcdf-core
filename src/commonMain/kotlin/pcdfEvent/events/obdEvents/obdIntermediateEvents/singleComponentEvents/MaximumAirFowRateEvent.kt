package pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents

import pcdfEvent.events.obdEvents.OBDCommand
import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class MaximumAirFowRateEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var rate: Int
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, -1) {
        rate = (data.substring(0, 2).toInt(16) * 10)
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            maximum_airflow_rate = rate
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Maximum Air Flow Rate: $rate ${
            OBDCommand.MAXIMUM_AIR_FLOW_RATE.unit
        }"
    }
}