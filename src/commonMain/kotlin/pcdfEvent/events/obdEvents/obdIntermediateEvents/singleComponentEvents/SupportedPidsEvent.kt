package pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class SupportedPidsEvent(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var supportedPids: MutableList<Int>
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {

    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, mutableListOf<Int>()) {
        var data1 = data.toLong(16).toString(2)
        // Fill up bit string with zeros in case its to short (has to consist of exactly 32 bits)
        while (data1.length < 32) {
            data1 = "0$data1"
        }
        for (i in 1..32) {
            if (data1[i - 1].code == 49) {
                supportedPids.add(i + pid)
            }
        }
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes,
            mode = mode,
            pid = pid,
            supported_pids = supportedPids.toTypedArray()
        )
        return pattern
    }

    override fun toString(): String {
        return super.toString() + "Supported Pids: ${ let {
            val sArr = mutableListOf<String>()
            supportedPids.forEach { 
                sArr.add(if (it < 16) {"0"} else {""} + it.toString(16).uppercase())
            }
            sArr.joinToString()
        } }"
    }
}