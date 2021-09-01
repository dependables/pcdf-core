package pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents

import pcdfEvent.events.obdEvents.OBDCommand
import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class EngineExhaustFlowRateEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var rate: Double
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
        rate = (data.toInt(16) * 0.2)
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            engine_exhaust_flow_rate = rate
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Engine Exhaust Flow Rate: $rate ${
            OBDCommand.ENGINE_EXHAUST_FLOW_RATE.unit
        }"
    }
}