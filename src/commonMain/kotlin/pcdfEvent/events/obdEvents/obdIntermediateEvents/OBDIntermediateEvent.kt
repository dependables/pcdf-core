package pcdfEvent.events.obdEvents.obdIntermediateEvents

import pcdfEvent.events.obdEvents.OBDEvent
import pcdfEvent.events.obdEvents.OBDCommand

/**
 * Abstract class for the intermediate events holding the parsed data.
 *
 * @param pid PID of the corresponding [OBDCommand].
 * @param mode Mode of the corresponding [OBDCommand].
 */
open class OBDIntermediateEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    var pid: Int,
    var mode: Int
) : OBDEvent(source, timestamp, bytes) {
    override fun toString(): String {
        return super.toString() + "  " + "mode: $mode  pid: $pid \n"
    }
}