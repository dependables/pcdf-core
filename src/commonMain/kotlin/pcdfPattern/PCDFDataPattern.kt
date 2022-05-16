package pcdfPattern

import kotlinx.serialization.Serializable

/**
 * Pattern for data property of the PCDF.
 * Used for JSON parsing and serialization.
 */
@Serializable
data class PCDFDataPattern(
    val pcdf_type: String? = null,
    val ppcdf_version: String? = null,
    val ipcdf_version: String? = null,
    var longitude: Double? = null,
    val latitude: Double? = null,
    val altitude: Double? = null,
    val gps_speed: Double? = null,
    val message: String? = null,

    // OBD Data properties
    val bytes: String? = null,
    val mode: Int? = null,
    val pid: Int? = null,
    val rpm: Double? = null,
    val throttlePosition: Double? = null,
    val engine_coolant_temperature: Int? = null,
    val ambient_air_temperature: Int? = null,
    val intake_air_temperature: Int? = null,
    val engine_oil_temperature: Int? = null,
    val maximum_airflow_rate: Int? = null,
    val maf_air_flow_rate: Double? = null,
    val maf_air_flow_sensor_a: Double? = null,
    val maf_air_flow_sensor_b: Double? = null,
    val commanded_egr: Double? = null,
    val egr_error: Double? = null,
    val fuel_tank_level_input: Double? = null,
    val fuel_air_equivalence_ratio: Double? = null,
    val engine_exhaust_flow_rate: Double? = null,
    val vin: String? = null,
    val fuel_type: String? = null,
    val catalyst_temperature_1_1: Double? = null,
    val catalyst_temperature_1_2: Double? = null,
    val catalyst_temperature_2_1: Double? = null,
    val catalyst_temperature_2_2: Double? = null,
    val engine_fuel_rate: Double? = null,
    val engine_fuel_rate_multi: Double? = null,
    val vehicle_fuel_rate_multi: Double? = null,
    val intake_air_temperature_sensor_1_1: Int? = null,
    val intake_air_temperature_sensor_1_2: Int? = null,
    val intake_air_temperature_sensor_1_3: Int? = null,
    val intake_air_temperature_sensor_2_1: Int? = null,
    val intake_air_temperature_sensor_2_2: Int? = null,
    val intake_air_temperature_sensor_2_3: Int? = null,
    val max_oxygen_sensor_voltage: Int? = null,
    val max_oxygen_sensor_current: Int? = null,
    val max_intake_manifold_absolut_pressure: Int? = null,
    val max_fuel_air_equivalence: Int? = null,
    val nox_sensor_1_1: Int? = null,
    val nox_sensor_1_2: Int? = null,
    val nox_sensor_2_1: Int? = null,
    val nox_sensor_2_2: Int? = null,
    val nox_sensor_corrected_1_1: Int? = null,
    val nox_sensor_corrected_1_2: Int? = null,
    val nox_sensor_corrected_2_1: Int? = null,
    val nox_sensor_corrected_2_2: Int? = null,
    val nox_sensor_alternative_1_1: Int? = null,
    val nox_sensor_alternative_1_2: Int? = null,
    val nox_sensor_alternative_2_1: Int? = null,
    val nox_sensor_alternative_2_2: Int? = null,
    val nox_sensor_corrected_alternative_1_1: Int? = null,
    val nox_sensor_corrected_alternative_1_2: Int? = null,
    val nox_sensor_corrected_alternative_2_1: Int? = null,
    val nox_sensor_corrected_alternative_2_2: Int? = null,
    var nox_averageReagentConsumption: Double? = null,
    var nox_averageDemandedReagentConsumption: Double? = null,
    var nox_reagentTankLevel: Double? = null,
    var nox_totalRunTimeWhileNOxWarningActivated: Long? = null,
    var scr_inducement_empty_tank: Boolean? = null,
    var scr_inducement_incorrect_reagent: Boolean? = null,
    var scr_inducement_deviation_reagent_consumption: Boolean? = null,
    var scr_inducement_nox_too_high: Boolean? = null,
    var scr_inducement_system_active: Boolean? = null,
    var scr_inducement_empty_tank_10k: Boolean? = null,
    var scr_inducement_incorrect_reagent_10k: Boolean? = null,
    var scr_inducement_deviation_reagent_consumption_10k: Boolean? = null,
    var scr_inducement_nox_too_high_10k: Boolean? = null,
    var scr_inducement_empty_tank_20k: Boolean? = null,
    var scr_inducement_incorrect_reagent_20k: Boolean? = null,
    var scr_inducement_deviation_reagent_consumption_20k: Boolean? = null,
    var scr_inducement_nox_too_high_20k: Boolean? = null,
    var scr_inducement_empty_tank_30k: Boolean? = null,
    var scr_inducement_incorrect_reagent_30k: Boolean? = null,
    var scr_inducement_deviation_reagent_consumption_30k: Boolean? = null,
    var scr_inducement_nox_too_high_30k: Boolean? = null,
    var scr_inducement_empty_tank_40k: Boolean? = null,
    var scr_inducement_incorrect_reagent_40k: Boolean? = null,
    var scr_inducement_deviation_reagent_consumption_40k: Boolean? = null,
    var scr_inducement_nox_too_high_40k: Boolean? = null,
    var scr_inducement_system_active_distance_10k: Int? = null,
    var scr_inducement_distance_travelled_current10k: Int? = null,
    var scr_inducement_system_active_distance_20k: Int? = null,
    var scr_inducement_system_active_distance_30k: Int? = null,
    var scr_inducement_system_active_distance_40k: Int? = null,
    var aftertreatment_status_particulateFilterRegenInProgress: Boolean? = null,
    var aftertreatment_status_particulateFilterActiveRegen: Boolean? = null,
    var aftertreatment_status_noxAdsorberRegenInProgress: Boolean? = null,
    var aftertreatment_status_noxAdsorberDesulfurizationInProgress: Boolean? = null,
    var aftertreatment_status_normalizedTriggerForPFRegen: Double? = null,
    var aftertreatment_status_averageTimeBetweenPFRegens: Int? = null,
    var aftertreatment_status_averageDistanceBetweenPFRegens: Int? = null,
    val particular_matter_1_1: Double? = null,
    val particular_matter_2_1: Double? = null,
    val oxygen_sensor_fuel_air_equivalence_ratio_1: Double? = null,
    val oxygen_sensor_voltage_1: Double? = null,
    val speed: Int? = null,
    val supported_pids: Array<Int>? = null,

    // Analyser properties
    val thc_concentration: Double? = null,
    val nmhc_concentration: Double? = null,
    val co_concentration: Double? = null,
    val co2_concentration: Double? = null,
    val nox_concentration: Double? = null,
    val no_concentration: Double? = null,
    val no2_concentration: Double? = null,
    val o2_concentration: Double? = null,
    val pn_concentration: Double? = null,
    val thc_mass: Double? = null,
    val ch4_mass: Double? = null,
    val nmhc_mass: Double? = null,
    val co_mass: Double? = null,
    val co2_mass: Double? = null,
    val nox_mass: Double? = null,
    val no_mass: Double? = null,
    val no2_mass: Double? = null,
    val o2_mass: Double? = null,
    val pn: Double? = null,

    // Lola properties
    val stream_name: String? = null,
    val stream_value: Double? = null
)
