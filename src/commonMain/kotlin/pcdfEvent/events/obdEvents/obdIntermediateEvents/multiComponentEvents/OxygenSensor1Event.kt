package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class OxygenSensor1Event(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var fuelAirEquivalenceRatio: Double,
    var sensorVoltage: Double
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
        fuelAirEquivalenceRatio = data.substring(0, 2).toInt(16).times(2.0).div(65536.0)
        sensorVoltage = data.substring(2, 4).toInt(16).times(8.0).div(65536.0)
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            oxygen_sensor_fuel_air_equivalence_ratio_1 = fuelAirEquivalenceRatio,
            oxygen_sensor_voltage_1 = sensorVoltage
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Oxygen Sensor 1:  FuelAirER: $fuelAirEquivalenceRatio  " +
                "Voltage: $sensorVoltage V"
    }
}