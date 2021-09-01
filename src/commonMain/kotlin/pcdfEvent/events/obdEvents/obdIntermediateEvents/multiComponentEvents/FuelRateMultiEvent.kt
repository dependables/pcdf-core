package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class FuelRateMultiEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var engineFuelRate: Double,
    var vehicleFuelRate: Double
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, -1.0, -1.0) {
        engineFuelRate = (data.substring(0, 2).toInt(16).times(0.02))
        vehicleFuelRate = (data.substring(2, 4).toInt(16).times(0.02))
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            engine_fuel_rate_multi = engineFuelRate,
            vehicle_fuel_rate_multi = vehicleFuelRate
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Fuel Rate: " +
                if (engineFuelRate != -1.0) {
                    " Engine FA: $engineFuelRate g/s"
                } else {
                    ""
                } +
                if (vehicleFuelRate != -1.0) {
                    " Vehicle FA: $vehicleFuelRate g/s"
                } else {
                    ""
                }
    }
}