package pcdfEvent.events.obdEvents.obdIntermediateEvents.reducedComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class NOXReducedEvent(
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
    var nox_ppm = -1

    init {
        handleSensorReadings()
    }

    /**
     * Convert the readings of sensors Bank 1 to Bank 2 into one final result for the NOx value
     * in ppm, assuming that the values of [sensor1_1] to [sensor2_2] are already in ppm.
     */
    private fun handleSensorReadings() {
        nox_ppm = when {
            //Both banks supported, we add both values
            sensor1_2 != -1 && sensor2_2 != -1 -> {
                sensor1_2 + sensor2_2
            }
            sensor1_1 != -1 && sensor2_2 != -1 -> {
                sensor1_1 + sensor2_2
            }
            sensor1_2 != -1 && sensor2_1 != -1 -> {
                sensor1_2 + sensor2_1
            }
            sensor1_1 != -1 && sensor2_1 != -1 -> {
                sensor1_1 + sensor2_1
            }

            //Only one bank supported, we prioritise the second sensor of each bank
            sensor1_2 != -1 -> {
                sensor1_2
            }
            sensor2_2 != -1 -> {
                sensor2_2
            }
            sensor1_1 != -1 -> {
                sensor1_1
            }
            sensor1_2 != -1 -> {
                sensor1_2
            }

            //No sensor supported
            else -> {
                -1
            }
        }
    }


    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            message = "Reduced data is not meant to generate patterns."
        )
        return pattern
    }

}