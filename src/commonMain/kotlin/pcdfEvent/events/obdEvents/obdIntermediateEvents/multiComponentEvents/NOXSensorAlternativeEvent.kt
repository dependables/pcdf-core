package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class NOXSensorAlternativeEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var sensor1_1: Int,
    var sensor1_2: Int,
    var sensor2_1: Int,
    var sensor2_2: Int
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, -1, -1, -1, -1) {
        // Byte parsing according to OBD-Standard
        var supported = data.substring(0, 2).toInt(16).toString(2).reversed()
        while (supported.length < 8) {
            supported += "0"
        }
        for (i in 0..3) {
            when (i) {
                0 -> {
                    sensor1_1 = if (supported[i] == '1' && supported[i + 4] == '0') {
                        data.substring(2, 6).toInt(16)
                    } else {
                        -1
                    }
                }
                1 -> {
                    sensor1_2 = if (supported[i] == '1' && supported[i + 4] == '0') {
                        data.substring(6, 10).toInt(16)
                    } else {
                        -1
                    }
                }
                3 -> {
                    sensor2_1 = if (supported[i] == '1' && supported[i + 4] == '0') {
                        data.substring(10, 14).toInt(16)
                    } else {
                        -1
                    }
                }
                4 -> {
                    sensor2_2 = if (supported[i] == '1' && supported[i + 4] == '0') {
                        data.substring(14, 18).toInt(16)
                    } else {
                        -1
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
            nox_sensor_alternative_1_1 = sensor1_1,
            nox_sensor_alternative_1_2 = sensor1_2,
            nox_sensor_alternative_2_1 = sensor2_1,
            nox_sensor_alternative_2_2 = sensor2_2
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "NOx Sensor: " +
                if (sensor1_1 != -1) {
                    " Sensor 1: $sensor1_1 ppm"
                } else {
                    ""
                } +
                if (sensor1_2 != -1) {
                    " Sensor 2: $sensor1_2 ppm"
                } else {
                    ""
                } +
                if (sensor2_1 != -1) {
                    " Sensor 3: $sensor2_1 ppm"
                } else {
                    ""
                } +
                if (sensor2_2 != -1) {
                    " Sensor 4: $sensor2_2 ppm"
                } else {
                    ""
                }
    }
}