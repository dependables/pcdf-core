package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class ParticularMatterEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var sensor1_1: Double,
    var sensor2_1: Double
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
        // Byte parsing according to OBD-Standard
        var supported = data.substring(0, 2).toInt(16).toString(2).reversed()
        while (supported.length < 2) {
            supported += "0"
        }
        if (supported[0] == '1') {
            sensor1_1 = (data.substring(2, 6).toInt(16).times(0.0125))
        }
        if (supported[1] == '1') {
            sensor2_1 = (data.substring(6, 10).toInt(16).times(0.0125))
        }
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            particular_matter_1_1 = sensor1_1,
            particular_matter_2_1 = sensor2_1
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Particular Matter Sensor_1: $sensor1_1 mg/m^3  Sensor_2: " +
                "$sensor2_1 mg/m^3"
    }
}