package pcdfEvent.events

import pcdfEvent.EventType
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

/**
 * Represents an PCDF Analyser-Event.
 */
class AnalyserEvent(
    source: String,
    timestamp: Long,
    val thc_concentration: Double?,
    val nmhc_concentration: Double?,
    val co_concentration: Double?,
    val co2_concentration: Double?,
    val nox_concentration: Double?,
    val no_concentration: Double?,
    val no2_concentration: Double?,
    val o2_concentration: Double?,
    val pn_concentration: Double?,
    val thc_mass: Double?,
    val ch4_mass: Double?,
    val nmhc_mass: Double?,
    val co_mass: Double?,
    val co2_mass: Double?,
    val nox_mass: Double?,
    val no_mass: Double?,
    val no2_mass: Double?,
    val o2_mass: Double?,
    val pn: Double? = null
) : PCDFEvent(source, EventType.ANALYSER, timestamp) {
    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            thc_concentration = thc_concentration,
            nmhc_concentration = nmhc_concentration,
            co_concentration = co_concentration,
            co2_concentration = co2_concentration,
            nox_concentration = nox_concentration,
            no_concentration = no_concentration,
            no2_concentration = no2_concentration,
            o2_concentration = o2_concentration,
            pn_concentration = pn_concentration,
            thc_mass = thc_mass,
            ch4_mass = ch4_mass,
            nmhc_mass = nmhc_mass,
            co_mass = co_mass,
            co2_mass = co2_mass,
            nox_mass = nox_mass,
            no_mass = no_mass,
            no2_mass = no2_mass,
            o2_mass = o2_mass,
            pn = pn
        )
        return pattern
    }

}