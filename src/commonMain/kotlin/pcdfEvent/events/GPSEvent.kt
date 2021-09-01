package pcdfEvent.events

import pcdfEvent.EventType
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern
import kotlin.math.roundToInt

/**
 * A PCDFEvent for GPS data.
 * @param speed Optional GPS speed.
 */
class GPSEvent(
    source: String,
    timestamp: Long,
    val longitude: Double,
    val latitude: Double,
    val altitude: Double,
    val speed: Double?
) : PCDFEvent(source, EventType.GPS, timestamp) {

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            longitude = longitude,
            latitude = latitude,
            altitude = altitude,
            gps_speed = speed
        )
        return pattern
    }

    override fun toString(): String {
        return "Longitude: $longitude  Latitude: $latitude  Altitude: $altitude  Speed: ${
            speed?.times(
                3.6
            )?.roundToInt()
        } km/h"
    }
}