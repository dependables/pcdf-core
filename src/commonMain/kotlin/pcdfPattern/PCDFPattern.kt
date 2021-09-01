package pcdfPattern

import pcdfEvent.PCDFEvent
import kotlinx.serialization.Serializable

/**
 *  Pattern class with all properties of PCDF.
 *  Every Event of a PCDF-File is parsed into this pattern and then into a PCDFEvent Object.
 *  Used for serialization and deserialization of PCDF-data.
 */

@Serializable
data class PCDFPattern(
    val source: String,
    val type: String,
    val timestamp: Long,
    var data: PCDFDataPattern?
) {

    /**
     * Parses this pattern into a corresponding [PCDFEvent].
     */
    fun toEvent(): PCDFEvent {
        val parser = PatternParser()
        //TODO: check whether we have to parse to intermediate or not
        return parser.parsePattern(this)
    }
}
