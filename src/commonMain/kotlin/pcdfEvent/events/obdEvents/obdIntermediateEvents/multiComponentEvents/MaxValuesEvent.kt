package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class MaxValuesEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var maxOxygenSensorVoltage: Int,
    var maxOxygenSensorCurrent: Int,
    var maxIntakeManifoldAbsolutPressure: Int,
    var maxFuelAirEquivalenceRatio: Int
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
        maxFuelAirEquivalenceRatio = (data.substring(0, 2).toInt(16))
        maxOxygenSensorVoltage = (data.substring(2, 4).toInt(16))
        maxOxygenSensorCurrent = (data.substring(4, 6).toInt(16))
        maxIntakeManifoldAbsolutPressure = (data.substring(6, 8).toInt(16))
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            max_oxygen_sensor_voltage = maxOxygenSensorVoltage,
            max_oxygen_sensor_current = maxOxygenSensorCurrent,
            max_intake_manifold_absolut_pressure = maxIntakeManifoldAbsolutPressure,
            max_fuel_air_equivalence = maxFuelAirEquivalenceRatio
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Max Values: " +
                if (maxFuelAirEquivalenceRatio != -1) {
                    " F-A Equivalence Ratio: $maxFuelAirEquivalenceRatio LAMBDA"
                } else {
                    ""
                } +
                if (maxOxygenSensorVoltage != -1) {
                    " Oxygen Sensor Voltage: $maxOxygenSensorVoltage V"
                } else {
                    ""
                } +
                if (maxOxygenSensorCurrent != -1) {
                    " Oxygen Sensor Current: $maxOxygenSensorCurrent mA"
                } else {
                    ""
                } +
                if (maxIntakeManifoldAbsolutPressure != -1) {
                    " Intake Manifold: $maxIntakeManifoldAbsolutPressure kPa"
                } else {
                    ""
                }
    }
}