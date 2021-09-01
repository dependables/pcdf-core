package pcdfEvent.events.obdEvents

import pcdfEvent.EventType
import pcdfEvent.events.obdEvents.OBDCommand.*
import pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents.*
import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent
import pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents.*
import pcdfEvent.PCDFEvent
import pcdfPattern.PCDFDataPattern
import pcdfPattern.PCDFPattern

/**
 * A PCDFEvent representing an OBD-Event with data received from an OBD-Adapter.
 * @param bytes The raw bytes received from the vehicle.
 */
open class OBDEvent(
    source: String,
    timestamp: Long,
    val bytes: String
) : PCDFEvent(source, EventType.OBD_RESPONSE, timestamp) {

    /**
     * Converts this event into an [OBDIntermediateEvent], the type depends on the respective
     * mode and PID.
     * Calls the secondary constructor of the event determined.
     * This constructor parses the raw [bytes] into the corresponding data according to the
     * OBDII-Standard.
     */
    override fun toIntermediate(): OBDIntermediateEvent {
        var tmpbytes = bytes
        // get the mode of the obd command: convert it to int and substract 40
        val mode = tmpbytes.substring(0, 2).toInt(16) - 0x40
        tmpbytes = tmpbytes.substring(2)
        // get the pid of the obd command
        val pid = tmpbytes.substring(0, 2).toInt(16)
        tmpbytes = tmpbytes.substring(2)

        // Get command type from mode and pid
        return when (OBDCommand.getCommand(mode, pid)) {
            // Call matching secondary constructor of the command's event type
            ENGINE_COOLANT_TEMPERATURE ->
                EngineCoolantTemperatureEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            RPM ->
                RPMEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            SPEED ->
                SpeedEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            INTAKE_AIR_TEMPERATURE ->
                IntakeAirTemperatureEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            MAF_AIR_FLOW_RATE ->
                MAFAirFlowRateEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            MAF_AIR_FLOW_RATE_SENSOR ->
                MAFSensorEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            OXYGEN_SENSOR_1 ->
                OxygenSensor1Event(tmpbytes, source, timestamp, bytes, pid, mode)
            COMMANDED_EGR ->
                CommandedEGREvent(tmpbytes, source, timestamp, bytes, pid, mode)
            FUEL_TANK_LEVEL_INPUT ->
                FuelTankLevelInputEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            CATALYST_TEMPERATURE_1_1 ->
                CatalystTemperature1_1Event(tmpbytes, source, timestamp, bytes, pid, mode)
            CATALYST_TEMPERATURE_1_2 ->
                CatalystTemperature1_2Event(tmpbytes, source, timestamp, bytes, pid, mode)
            CATALYST_TEMPERATURE_2_1 ->
                CatalystTemperature2_1Event(tmpbytes, source, timestamp, bytes, pid, mode)
            CATALYST_TEMPERATURE_2_2 ->
                CatalystTemperature2_2Event(tmpbytes, source, timestamp, bytes, pid, mode)
            FUEL_AIR_EQUIVALENCE_RATIO ->
                FuelAirEquivalenceRatioEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            AMBIENT_AIR_TEMPERATURE ->
                AmbientAirTemperatureEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            MAX_VALUES ->
                MaxValuesEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            MAXIMUM_AIR_FLOW_RATE ->
                MaximumAirFowRateEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            FUEL_TYPE ->
                FuelTypeEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            ENGINE_OIL_TEMPERATURE ->
                EngineOilTemperatureEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            INTAKE_AIR_TEMPERATURE_SENSOR ->
                IntakeAirTemperatureEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            NOX_SENSOR ->
                NOXSensorEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            NOX_SENSOR_CORRECTED ->
                NOXSensorCorrectedEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            NOX_SENSOR_ALTERNATIVE ->
                NOXSensorAlternativeEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            NOX_SENSOR_CORRECTED_ALTERNATIVE ->
                NOXSensorCorrectedAlternativeEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            PARTICULAR_MATTER_SENSOR ->
                ParticularMatterEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            ENGINE_FUEL_RATE ->
                FuelRateEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            ENGINE_FUEL_RATE_MULTI ->
                FuelRateMultiEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            ENGINE_EXHAUST_FLOW_RATE ->
                EngineExhaustFlowRateEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            EGR_ERROR ->
                EGRErrorEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            VIN ->
                VINEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            SUPPORTED_PIDS_1_00,
            SUPPORTED_PIDS_1_32,
            SUPPORTED_PIDS_1_64,
            SUPPORTED_PIDS_1_96,
            SUPPORTED_PIDS_1_128,
            SUPPORTED_PIDS_1_160,
            SUPPORTED_PIDS_1_192,
            SUPPORTED_PIDS_9_00 ->
                SupportedPidsEvent(tmpbytes, source, timestamp, bytes, pid, mode)
            null -> throw Exception("There is no such pid and mode available")
        }
    }

    override fun getPattern(): PCDFPattern {
        val pattern = super.getPattern()
        pattern.data = PCDFDataPattern(
            bytes = bytes
        )
        return pattern
    }

    override fun toString(): String {
        return "bytes: $bytes"
    }
}