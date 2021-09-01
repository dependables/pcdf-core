package pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class FuelTypeEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var fueltype: String
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, "") {
        fueltype = when (data.toInt(16)) {
            0 -> "Not available"
            1 -> "Gasoline"
            2 -> "Methanol"
            3 -> "Ethanol"
            4 -> "Diesel"
            5 -> "LPG"
            6 -> "CNG"
            7 -> "Propane"
            8 -> "Electric"
            9 -> "Bifuel running Gasoline"
            10 -> "Bifuel running Methanol"
            11 -> "Bifuel running Ethanol"
            12 -> "Bifuel running LPG"
            13 -> "Bifuel running CNG"
            14 -> "Bifuel running Propane"
            15 -> "Bifuel running Electricity"
            16 -> "Bifuel running electric and combustion engine"
            17 -> "Hybrid gasoline"
            18 -> "Hybrid Ethanol"
            19 -> "Hybrid Diesel"
            20 -> "Hybrid Electric"
            21 -> "Hybrid running electric and combustion engine"
            22 -> "Hybrid Regenerative"
            23 -> "Bifuel running diesel"
            else -> "Not mapped"
        }
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            fuel_type = fueltype
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Fuel Type: $fueltype"
    }
}