package pcdfEvent.events

import pcdfEvent.EventType
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

/**
 * A PCDFEvent for Meta data.
 * Starts every PCDF file and contains information about the used type and version.
 */
class MetaEvent(
    source: String,
    timestamp: Long,
    val pcdf_type: String,
    val ppcdf_version: String?,
    val ipcdf_version: String?
) : PCDFEvent(source, EventType.META, timestamp) {

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            pcdf_type = pcdf_type,
            ppcdf_version = ppcdf_version,
            ipcdf_version = ipcdf_version
        )
        return pattern
    }

    override fun toString(): String {
        return "PCDF Type: $pcdf_type  Version: " +
                if (pcdf_type == "PERSISTENT") {
                    ppcdf_version
                } else {
                    ipcdf_version
                }
    }
}