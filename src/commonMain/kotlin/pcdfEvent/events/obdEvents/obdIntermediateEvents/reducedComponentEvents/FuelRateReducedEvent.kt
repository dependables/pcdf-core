package pcdfEvent.events.obdEvents.obdIntermediateEvents.reducedComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class FuelRateReducedEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var engineFuelRate: Double,
    var vehicleFuelRate: Double
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    var fuelRate: Double = -1.0

    init {
        handleSensorReadings()
    }

    /**
     * Convert the readings of engine and/or vehicle fuel rate to one fuel rate value.
     */
    private fun handleSensorReadings() {
        fuelRate = when {
            vehicleFuelRate != -1.0 -> {
                vehicleFuelRate
            }
            engineFuelRate != -1.0 -> {
                engineFuelRate
            }
            else -> {
                -1.0
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