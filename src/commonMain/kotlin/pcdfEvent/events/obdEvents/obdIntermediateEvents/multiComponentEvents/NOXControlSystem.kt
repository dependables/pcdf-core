package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class NOXControlSystem(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var averageReagentConsumption: Double?,
    var averageDemandedReagentConsumption: Double?,
    var reagentTankLevel: Double?,
    var totalRunTimeWhileNOxWarningActivated: Long?
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {
    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, null, null, null, null) {
        // Byte parsing according to OBD-Standard
        var supported = data.substring(0, 2).toInt(16).toString(2).reversed()
        while (supported.length < 8) {
            supported += "0"
        }

        averageReagentConsumption = if (supported[0] == '1') {
            data.substring(2, 6).toInt(16).toDouble() * 0.005
        } else {
            null
        }

        averageDemandedReagentConsumption = if (supported[1] == '1') {
            data.substring(6, 10).toInt(16).toDouble() * 0.005
        } else {
            null
        }

        reagentTankLevel = if (supported[2] == '1') {
            data.substring(10, 12).toInt(16).toDouble() * 100.0 / 255.0
        } else {
            null
        }

        totalRunTimeWhileNOxWarningActivated = if (supported[3] == '1') {
            data.substring(12, 20).toLong(16)
        } else {
            null
        }

    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            nox_averageReagentConsumption = averageReagentConsumption,
            nox_averageDemandedReagentConsumption = averageDemandedReagentConsumption,
            nox_reagentTankLevel = reagentTankLevel,
            nox_totalRunTimeWhileNOxWarningActivated = totalRunTimeWhileNOxWarningActivated
        )
        return pattern
    }

    override fun toString(): String {
        val s1 = if (averageReagentConsumption == null) "n/a" else "${averageReagentConsumption} L/h"
        val s2 = if (averageDemandedReagentConsumption == null) "n/a" else "${averageDemandedReagentConsumption} L/h"
        val s3 = if (reagentTankLevel == null) "n/a" else "${reagentTankLevel} %"
        val s4 = if (totalRunTimeWhileNOxWarningActivated == null) "n/a" else "${totalRunTimeWhileNOxWarningActivated} s"
        return super.toString() + "NOx Control System: average reagent consumption: $s1, average demanded reagent consumption: $s2, reagent tank level: $s3, total run time by the engine while NOx warning mode is activated: $s4"
    }
}