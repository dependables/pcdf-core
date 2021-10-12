package pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents

import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

class AftertreatmentStatus(
    source: String,
    timestamp: Long,
    bytes: String,
    pid: Int,
    mode: Int,
    var particulateFilterRegenInProgress: Boolean?,
    var particulateFilterActiveRegen: Boolean?,     // false = passive
    var noxAdsorberRegenInProgress: Boolean?,   // false = Adsorption in progress
    var noxAdsorberDesulfurizationInProgress: Boolean?,
    var normalizedTriggerForPFRegen: Double?,    // in percent
    var averageTimeBetweenPFRegens: Int?,   // minutes
    var averageDistanceBetweenPFRegens: Int?    // km
) : OBDIntermediateEvent(source, timestamp, bytes, pid, mode) {
    // Secondary constructor only used by convert function.
    constructor(
        data: String,
        source: String,
        timestamp: Long,
        bytes: String,
        pid: Int,
        mode: Int
    ) : this(source, timestamp, bytes, pid, mode, null, null, null, null, null, null, null) {
        // Byte parsing according to OBD-Standard
        var supported = data.substring(0, 2).toInt(16).toString(2).reversed()
        while (supported.length < 8) {
            supported += "0"
        }
        val pfRegenStatusSupported = supported[0] == '1'
        val pfRegenTypeSupported = supported[1] == '1'
        val noxAdsorberRegenStatusSupported = supported[2] == '1'
        val noxAdsorberDesulfStatusSupported = supported[3] == '1'
        val normalizedTriggerSupported = supported[4] == '1'
        val avgTimePFSupported = supported[5] == '1'
        val avgDistPFSupported = supported[6] == '1'

        var aftertreatmentStatus = data.substring(2,4).toInt(16).toString(2).reversed()
        while (aftertreatmentStatus.length < 4) {
            aftertreatmentStatus += "0"
        }
        particulateFilterRegenInProgress = if (pfRegenStatusSupported) {
            aftertreatmentStatus[0]  == '1'
        } else {
            null
        }

        particulateFilterActiveRegen = if(pfRegenTypeSupported) {
            aftertreatmentStatus[1] == '1'
        } else {
            null
        }

        noxAdsorberRegenInProgress = if(noxAdsorberRegenStatusSupported) {
            aftertreatmentStatus[2] == '1'
        } else {
            null
        }

        noxAdsorberDesulfurizationInProgress = if(noxAdsorberDesulfStatusSupported) {
            aftertreatmentStatus[3] == '1'
        } else {
            null
        }

        normalizedTriggerForPFRegen = if (normalizedTriggerSupported) {
            data.substring(4, 6).toInt(16).toDouble() * 100.0 / 255.0
        } else {
            null
        }

        averageTimeBetweenPFRegens = if (avgTimePFSupported) {
            data.substring(6, 10).toInt(16)
        } else {
            null
        }

        averageDistanceBetweenPFRegens = if (avgDistPFSupported) {
            data.substring(10, 14).toInt(16)
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
            aftertreatment_status_particulateFilterRegenInProgress = particulateFilterRegenInProgress,
            aftertreatment_status_particulateFilterActiveRegen = particulateFilterActiveRegen,
            aftertreatment_status_noxAdsorberRegenInProgress = noxAdsorberRegenInProgress,
            aftertreatment_status_noxAdsorberDesulfurizationInProgress = noxAdsorberDesulfurizationInProgress,
            aftertreatment_status_normalizedTriggerForPFRegen = normalizedTriggerForPFRegen,
            aftertreatment_status_averageTimeBetweenPFRegens = averageTimeBetweenPFRegens,
            aftertreatment_status_averageDistanceBetweenPFRegens = averageDistanceBetweenPFRegens
        )
        return pattern
    }

    override fun toString(): String {
        val s1 = if (particulateFilterRegenInProgress == null) "n/a" else if(particulateFilterRegenInProgress == true) "Yes" else "No"
        val s2 = if (particulateFilterActiveRegen == null) "n/a" else if(particulateFilterActiveRegen == true) "Active" else "Passive"
        val s3 = if (noxAdsorberRegenInProgress == null) "n/a" else if(noxAdsorberRegenInProgress == true) "Desorption" else "Adsorption"
        val s4 = if (noxAdsorberDesulfurizationInProgress == null) "n/a" else if(noxAdsorberDesulfurizationInProgress == true) "Yes" else "No"
        val s5 = if (normalizedTriggerForPFRegen == null) "n/a" else "${normalizedTriggerForPFRegen} %"
        val s6 = if (averageTimeBetweenPFRegens == null) "n/a" else "${averageTimeBetweenPFRegens} minutes"
        val s7 = if (averageDistanceBetweenPFRegens == null) "n/a" else "${averageDistanceBetweenPFRegens} km"
        return super.toString() + "Aftertreatment Status: PF regeneration in progress: $s1, PF regeneration type: $s2, NOx adsorber status: $s3, NOx adsorber desulfurization in progress: $s4, PF level: $s5, average time between PF regenerations: $s6, average distance between PF regenerations: $s7"
    }
}