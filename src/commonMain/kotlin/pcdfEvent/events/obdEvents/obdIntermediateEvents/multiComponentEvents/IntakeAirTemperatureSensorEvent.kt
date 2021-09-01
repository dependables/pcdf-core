package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class IntakeAirTemperatureSensorEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var sensor1_1: Int,
    var sensor1_2: Int,
    var sensor1_3: Int,
    var sensor2_1: Int,
    var sensor2_2: Int,
    var sensor2_3: Int
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, -1, -1, -1, -1, -1, -1) {
        /** First byte represents supported sensors by bits
        e.g. first bit == 0 <=> sensor 1 bank 1 not supported */
        val supported = data.substring(0, 2).toInt(16).toString(2).reversed()
        for (i in 0..6) {
            if (i < supported.length) {
                if (supported[i] == '1') {
                    when (i) {
                        0 -> sensor1_1 = (data.substring(2, 4).toInt(16) - 40)
                        1 -> sensor1_2 = (data.substring(4, 6).toInt(16) - 40)
                        2 -> sensor1_3 = (data.substring(6, 8).toInt(16) - 40)
                        3 -> sensor2_1 = (data.substring(8, 10).toInt(16) - 40)
                        4 -> sensor2_2 = (data.substring(10, 12).toInt(16) - 40)
                        5 -> sensor2_3 = (data.substring(12, 14).toInt(16) - 40)
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
            intake_air_temperature_sensor_1_1 = sensor1_1,
            intake_air_temperature_sensor_1_2 = sensor1_2,
            intake_air_temperature_sensor_1_3 = sensor1_3,
            intake_air_temperature_sensor_2_1 = sensor2_1,
            intake_air_temperature_sensor_2_2 = sensor2_2,
            intake_air_temperature_sensor_2_3 = sensor2_3
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Intake Air Temperature: " +
                if (sensor1_1 != -1) {
                    " Sensor 1: $sensor1_1 °C"
                } else {
                    ""
                } +
                if (sensor1_2 != -1) {
                    " Sensor 2: $sensor1_2 °C"
                } else {
                    ""
                } +
                if (sensor1_3 != -1) {
                    " Sensor 3: $sensor1_3 °C"
                } else {
                    ""
                } +
                if (sensor2_1 != -1) {
                    " Sensor 4: $sensor2_1 °C"
                } else {
                    ""
                } +
                if (sensor2_2 != -1) {
                    " Sensor 5: $sensor2_2 °C"
                } else {
                    ""
                } +
                if (sensor2_3 != -1) {
                    " Sensor 6: $sensor2_3 °C"
                } else {
                    ""
                }
    }
}