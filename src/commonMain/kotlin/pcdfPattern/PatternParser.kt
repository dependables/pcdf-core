package pcdfPattern

import pcdfEvent.events.obdEvents.OBDCommand
import pcdfEvent.events.obdEvents.OBDCommand.*
import pcdfEvent.events.obdEvents.OBDEvent
import pcdfEvent.events.obdEvents.obdIntermediateEvents.multiComponentEvents.*
import pcdfEvent.events.obdEvents.obdIntermediateEvents.singleComponentEvents.*
import pcdfEvent.PCDFEvent
import pcdfEvent.events.*
import pcdfEvent.events.obdEvents.obdIntermediateEvents.OBDIntermediateEvent

/**
 * Parses [PCDFPattern] object into [PCDFEvent].
 */
class PatternParser {

    /**
     * Main function to parse a [PCDFPattern] into a [PCDFEvent].
     * @param pattern Pattern to be parsed.
     * @param intermediate Decides whether the parsed pattern, if it represents OBD data, has to
     * be parsed into an [OBDIntermediateEvent].
     */
    fun parsePattern(pattern: PCDFPattern): PCDFEvent {
        when (pattern.type) {
            "OBD_RESPONSE" -> {
                // Check whether the OBD response data is already in intermediate format
                return if (pattern.data!!.pid != null) {
                    parseIntermediateOBD(pattern)
                } else {
                    parsePersistentOBD(pattern)
                }
            }
            "GPS" -> {
                return parseGPS(pattern)
            }
            "ERROR" -> {
                return parseError(pattern)
            }
            "ANALYSER" -> {
                return parseAnalyserData(pattern)
            }
            "META" -> {
                return parseMeta(pattern)
            }
            "LOLA" -> {
                return parseLolaData(pattern)
            }
            else -> {
                throw Error("pattern was of invalid event type: only one Meta Event allowed")
            }
        }
    }

    /**
     * Parses meta event pattern into pcdf event.
     * @param pattern Pattern to be parsed.
     */
    fun parseMeta(pattern: PCDFPattern): MetaEvent {
        if (pattern.type == "META") {
            return MetaEvent(
                pattern.source,
                pattern.timestamp,
                pattern.data!!.pcdf_type!!,
                pattern.data!!.ppcdf_version,
                pattern.data!!.ipcdf_version
            )
        } else {
            throw Error("pcdfEvent.EventType must be META")
        }
    }

    /**
     * Parses gps event pattern into pcdf event.
     * @param pattern Pattern to be parsed.
     */
    private fun parseGPS(pattern: PCDFPattern): GPSEvent {
        if (pattern.type == "GPS") {
            return GPSEvent(
                pattern.source,
                pattern.timestamp,
                pattern.data!!.longitude!!.toDouble(),
                pattern.data!!.latitude!!.toDouble(),
                pattern.data!!.altitude!!.toDouble(),
                pattern.data!!.gps_speed
            )
        } else {
            throw Error("pcdfEvent.EventType must be GPS")
        }
    }

    /**
     * Parses error event pattern into pcdf event.
     * @param pattern Pattern to be parsed.
     */
    private fun parseError(pattern: PCDFPattern): ErrorEvent {
        if (pattern.type == "ERROR") {
            return ErrorEvent(
                pattern.source,
                pattern.timestamp,
                pattern.data!!.message!!
            )
        } else {
            throw Error("event type must be ERROR")
        }
    }

    /**
     * Parses intermediate OBD event pattern into [PCDFEvent].
     * Automatically detects used PID and only parses the related properties .
     * @param pattern Pattern to be parsed.
     */
    private fun parseIntermediateOBD(pattern: PCDFPattern): PCDFEvent {
        if (pattern.type == "OBD_RESPONSE") {
            val source = pattern.source
            val timestamp = pattern.timestamp
            val bytes = pattern.data!!.bytes!!
            val pid = pattern.data!!.pid!!
            val mode = pattern.data!!.mode!!

            return when (OBDCommand.getCommand(mode, pid)) {
                ENGINE_COOLANT_TEMPERATURE -> EngineCoolantTemperatureEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.engine_coolant_temperature!!
                )
                RPM -> RPMEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.rpm!!
                )
                SPEED -> SpeedEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.speed!!
                )
                ABSOLUTE_THROTTLE_POSITION -> AbsoluteThrottlePositionEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.throttlePosition!!
                )
                RELATIVE_THROTTLE_POSITION -> RelativeThrottlePositionEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.throttlePosition!!
                )
                INTAKE_AIR_TEMPERATURE -> IntakeAirTemperatureEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.intake_air_temperature!!
                )
                MAF_AIR_FLOW_RATE -> MAFAirFlowRateEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.maf_air_flow_rate!!
                )
                MAF_AIR_FLOW_RATE_SENSOR -> MAFSensorEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.maf_air_flow_sensor_a!!,
                    pattern.data!!.maf_air_flow_sensor_b!!
                )
                OXYGEN_SENSOR_1 -> OxygenSensor1Event(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.oxygen_sensor_fuel_air_equivalence_ratio_1!!,
                    pattern.data!!.oxygen_sensor_voltage_1!!
                )
                COMMANDED_EGR -> CommandedEGREvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.commanded_egr!!
                )
                FUEL_TANK_LEVEL_INPUT -> FuelTankLevelInputEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.fuel_tank_level_input!!
                )
                CATALYST_TEMPERATURE_1_1 -> CatalystTemperature1_1Event(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.catalyst_temperature_1_1!!
                )
                CATALYST_TEMPERATURE_1_2 -> CatalystTemperature1_2Event(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.catalyst_temperature_1_2!!
                )
                CATALYST_TEMPERATURE_2_1 -> CatalystTemperature2_1Event(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.catalyst_temperature_2_1!!
                )
                CATALYST_TEMPERATURE_2_2 -> CatalystTemperature2_2Event(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.catalyst_temperature_2_2!!
                )
                ENGINE_FUEL_RATE_MULTI -> FuelRateMultiEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.engine_fuel_rate_multi!!,
                    pattern.data!!.vehicle_fuel_rate_multi!!
                )
                FUEL_AIR_EQUIVALENCE_RATIO -> FuelAirEquivalenceRatioEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.fuel_air_equivalence_ratio!!
                )
                AMBIENT_AIR_TEMPERATURE -> AmbientAirTemperatureEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.ambient_air_temperature!!
                )
                MAX_VALUES -> MaxValuesEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.max_oxygen_sensor_voltage!!,
                    pattern.data!!.max_oxygen_sensor_current!!,
                    pattern.data!!.max_intake_manifold_absolut_pressure!!,
                    pattern.data!!.max_fuel_air_equivalence!!
                )
                MAXIMUM_AIR_FLOW_RATE -> MaximumAirFowRateEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.maximum_airflow_rate!!
                )
                FUEL_TYPE -> FuelTypeEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.fuel_type!!
                )
                ENGINE_OIL_TEMPERATURE -> EngineOilTemperatureEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.engine_oil_temperature!!
                )
                INTAKE_AIR_TEMPERATURE_SENSOR -> IntakeAirTemperatureSensorEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.intake_air_temperature_sensor_1_1!!,
                    pattern.data!!.intake_air_temperature_sensor_1_2!!,
                    pattern.data!!.intake_air_temperature_sensor_1_3!!,
                    pattern.data!!.intake_air_temperature_sensor_2_1!!,
                    pattern.data!!.intake_air_temperature_sensor_2_2!!,
                    pattern.data!!.intake_air_temperature_sensor_2_3!!
                )
                NOX_SENSOR -> NOXSensorEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.nox_sensor_1_1!!,
                    pattern.data!!.nox_sensor_1_2!!,
                    pattern.data!!.nox_sensor_2_1!!,
                    pattern.data!!.nox_sensor_2_2!!
                )
                NOX_SENSOR_CORRECTED -> NOXSensorCorrectedEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.nox_sensor_corrected_1_1!!,
                    pattern.data!!.nox_sensor_corrected_1_2!!,
                    pattern.data!!.nox_sensor_corrected_2_1!!,
                    pattern.data!!.nox_sensor_corrected_2_2!!
                )
                NOX_SENSOR_ALTERNATIVE -> NOXSensorAlternativeEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.nox_sensor_alternative_1_1!!,
                    pattern.data!!.nox_sensor_alternative_1_2!!,
                    pattern.data!!.nox_sensor_alternative_2_1!!,
                    pattern.data!!.nox_sensor_alternative_2_2!!
                )
                NOX_SENSOR_CORRECTED_ALTERNATIVE -> NOXSensorCorrectedAlternativeEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.nox_sensor_corrected_alternative_1_1!!,
                    pattern.data!!.nox_sensor_corrected_alternative_1_2!!,
                    pattern.data!!.nox_sensor_corrected_alternative_2_1!!,
                    pattern.data!!.nox_sensor_corrected_alternative_2_2!!
                )
                PARTICULAR_MATTER_SENSOR -> ParticularMatterEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.particular_matter_1_1!!,
                    pattern.data!!.particular_matter_2_1!!
                )
                ENGINE_FUEL_RATE -> FuelRateEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.engine_fuel_rate!!
                )
                ENGINE_EXHAUST_FLOW_RATE -> EngineExhaustFlowRateEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.engine_exhaust_flow_rate!!
                )
                EGR_ERROR -> EGRErrorEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.egr_error!!
                )
                VIN -> VINEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.vin!!
                )
                SUPPORTED_PIDS_1_00,
                SUPPORTED_PIDS_1_32,
                SUPPORTED_PIDS_1_64,
                SUPPORTED_PIDS_1_96,
                SUPPORTED_PIDS_1_128,
                SUPPORTED_PIDS_1_160,
                SUPPORTED_PIDS_1_192,
                SUPPORTED_PIDS_9_00 -> SupportedPidsEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode,
                    pattern.data!!.supported_pids!!.toMutableList()
                )
                null -> throw Exception("There is no such pid and mode available")
                else -> OBDIntermediateEvent(
                    source,
                    timestamp,
                    bytes,
                    pid,
                    mode
                )
            }
        } else {
            throw Exception("Event type must be OBD_RESPONSE")
        }
    }

    /**
     * parses obd persistent event pattern into pcdf event
     * @param pattern pattern to be parsed
     */
    private fun parsePersistentOBD(pattern: PCDFPattern): PCDFEvent {
        if (pattern.type == "OBD_RESPONSE") {
            return OBDEvent(
                pattern.source,
                pattern.timestamp,
                pattern.data!!.bytes!!
            )
        } else {
            throw Error("Event type must be OBD_RESPONSE")
        }
    }

    /**
     * parses analyser event info into pcdf event
     * @param pattern pattern to be parsed
     */
    private fun parseAnalyserData(pattern: PCDFPattern): PCDFEvent {
        if (pattern.type == "ANALYSER") {
            return AnalyserEvent(
                pattern.source,
                pattern.timestamp,
                pattern.data!!.thc_concentration,
                pattern.data!!.nmhc_concentration,
                pattern.data!!.co_concentration,
                pattern.data!!.co2_concentration,
                pattern.data!!.nox_concentration,
                pattern.data!!.no_concentration,
                pattern.data!!.no2_concentration,
                pattern.data!!.o2_concentration,
                pattern.data!!.pn_concentration,
                pattern.data!!.thc_mass,
                pattern.data!!.ch4_mass,
                pattern.data!!.nmhc_mass,
                pattern.data!!.co_mass,
                pattern.data!!.co2_mass,
                pattern.data!!.nox_mass,
                pattern.data!!.no_mass,
                pattern.data!!.no2_mass,
                pattern.data!!.o2_mass,
                pattern.data!!.pn
            )
        } else {
            throw Error("Event type must be ANALYSER")
        }
    }

    private fun parseLolaData(pattern: PCDFPattern): PCDFEvent {
        if (pattern.type == "LOLA") {
            return LolaEvent(
                pattern.source,
                pattern.timestamp,
                pattern.data!!.stream_name!!,
                pattern.data!!.stream_value!!
            )
        } else {
            throw Error("Event type must be Lola")
        }
    }
}
