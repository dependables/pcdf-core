package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class MAFSensorEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var mafSensorA: Double,
    var mafSensorB: Double
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
        var supported = data.substring(0, 2).toInt(16).toString(2).reversed()
        while (supported.length < 8) {
            supported += "0"
        }

        for (i in 0..1) {
            when (i) {
                0 -> {
                    mafSensorA = if (supported[i] == '1' && supported[i + 4] == '0') {
                        data.substring(2, 6).toInt(16).times(0.03125)
                    } else {
                        -1.0
                    }
                }
                1 -> {
                    mafSensorB = if (supported[i] == '1' && supported[i + 4] == '0') {
                        data.substring(6, 10).toInt(16).times(0.03125)
                    } else {
                        -1.0
                    }
                }
            }
        }
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            maf_air_flow_sensor_a = mafSensorA,
            maf_air_flow_sensor_b = mafSensorB
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "MAF Sensor: " +
                if (mafSensorA != -1.0) {
                    " Sensor A: $mafSensorA g/s"
                } else {
                    ""
                } +
                if (mafSensorB != -1.0) {
                    " Sensor B: $mafSensorB g/s"
                } else {
                    ""
                }
    }
}