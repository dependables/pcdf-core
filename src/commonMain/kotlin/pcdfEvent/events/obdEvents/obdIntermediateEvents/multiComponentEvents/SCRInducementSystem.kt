package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class SCRInducementSystem(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var stateActual: InducementState,
    var inducementSystemActive: Boolean,
    var state10K: InducementState,
    var state20K: InducementState,
    var state30k: InducementState,
    var state40k: InducementState,
    var distanceWithActiveSystem10K: Int,
    var distanceTravelledInCurrent10KBlock: Int,
    var distanceWithActiveSystem20K: Int,
    var distanceWithActiveSystem30K: Int,
    var distanceWithActiveSystem40K: Int
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode)  {



    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode,
        inducementStateFromBits(bitifyHalfByte(data[1])),  // bits 0-3 of byte A
        bitifyHalfByte(data[0])[3],  // bit 7 of byte A is true
        inducementStateFromBits(bitifyHalfByte(data[3])),
        inducementStateFromBits(bitifyHalfByte(data[2])),
        inducementStateFromBits(bitifyHalfByte(data[5])),
        inducementStateFromBits(bitifyHalfByte(data[4])),
        distanceFromTwoBytes(data.substring(6,10)),
        distanceFromTwoBytes(data.substring(10,14)),
        distanceFromTwoBytes(data.substring(14,18)),
        distanceFromTwoBytes(data.substring(18,22)),
        distanceFromTwoBytes(data.substring(22,26)))

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            scr_inducement_empty_tank = stateActual.emptyReagentTank,
            scr_inducement_deviation_reagent_consumption = stateActual.deviationOfReagentConsumption,
            scr_inducement_incorrect_reagent = stateActual.incorrectReagent,
            scr_inducement_nox_too_high = stateActual.noxEmissionTooHigh,
            scr_inducement_system_active = inducementSystemActive,
            scr_inducement_empty_tank_10k = state10K.emptyReagentTank,
            scr_inducement_deviation_reagent_consumption_10k = state10K.deviationOfReagentConsumption,
            scr_inducement_incorrect_reagent_10k = state10K.incorrectReagent,
            scr_inducement_nox_too_high_10k = state10K.noxEmissionTooHigh,
            scr_inducement_empty_tank_20k = state20K.emptyReagentTank,
            scr_inducement_deviation_reagent_consumption_20k = state20K.deviationOfReagentConsumption,
            scr_inducement_incorrect_reagent_20k = state20K.incorrectReagent,
            scr_inducement_nox_too_high_20k = state20K.noxEmissionTooHigh,
            scr_inducement_empty_tank_30k = state30k.emptyReagentTank,
            scr_inducement_deviation_reagent_consumption_30k = state30k.deviationOfReagentConsumption,
            scr_inducement_incorrect_reagent_30k = state30k.incorrectReagent,
            scr_inducement_nox_too_high_30k = state30k.noxEmissionTooHigh,
            scr_inducement_empty_tank_40k = state40k.emptyReagentTank,
            scr_inducement_deviation_reagent_consumption_40k = state40k.deviationOfReagentConsumption,
            scr_inducement_incorrect_reagent_40k = state40k.incorrectReagent,
            scr_inducement_nox_too_high_40k = state40k.noxEmissionTooHigh,
            scr_inducement_system_active_distance_10k = distanceWithActiveSystem10K,
            scr_inducement_distance_travelled_current10k = distanceTravelledInCurrent10KBlock,
            scr_inducement_system_active_distance_20k = distanceWithActiveSystem20K,
            scr_inducement_system_active_distance_30k = distanceWithActiveSystem30K,
            scr_inducement_system_active_distance_40k = distanceWithActiveSystem40K
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + " inducement system active: $inducementSystemActive, current state: $stateActual, 10k state: $state10K, 20k state: $state20K, 30k state: $state30k, 40k state: $state40k, distance travelled in current 10k block: $distanceTravelledInCurrent10KBlock km, system active in 10k block: $distanceWithActiveSystem10K km, , system active in 20k block: $distanceWithActiveSystem20K km, , system active in 30k block: $distanceWithActiveSystem30K km, , system active in 40k block: $distanceWithActiveSystem40K km"
    }





    companion object {
        private fun bitifyHalfByte(hexChar: Char): List<Boolean> {
//            if (hexByteString.length != 2) {
//                throw Error("Expected byte string of length 2.")
//            }
            var binary = hexChar.digitToInt(16).toString(2).reversed()
            while (binary.length < 4) {
                binary += "0"
            }
            return binary.map {
                when (it) {
                    '1' -> true
                    '0' -> false
                    else -> throw Error("Unexpected character")
                }
            }
        }

        private fun inducementStateFromBits(bits: List<Boolean>): InducementState {
            if (bits.size != 4) {
                throw Error("Unexpected number of bits")
            }
            return InducementState(bits[0], bits[1], bits[2], bits[3])
        }

        private fun distanceFromTwoBytes(bytes: String): Int {
            if (bytes.length != 4) {
                throw Error("Unexpected number of bytes (expected 2 bytes / 4 hex chars, git ${bytes.length} hex chars)!")
            }
            return bytes.toInt(16)
        }
    }


    data class InducementState(
        val emptyReagentTank: Boolean,
        val incorrectReagent: Boolean,
        val deviationOfReagentConsumption: Boolean,
        val noxEmissionTooHigh: Boolean
    ) {
        override fun toString(): String {
            return "empty tank: $emptyReagentTank, incorrect reagent: $incorrectReagent, deviation of reagent consumption: $deviationOfReagentConsumption, NOx emissions too high: $noxEmissionTooHigh"
        }
    }
}