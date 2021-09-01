package pcdfEvent.events.obdEvents

/**
 * Enum representing OBD Commands.
 * Helps to identify the intermediate data type in which an persistent bytes property has to be
 * converted to.
 * @param mode: Specifies the diagnostic test mode.
 * @param pid: Represents the used pid.
 */
enum class OBDCommand(val mode: Int, val pid: Int, val unit: String) {
    ENGINE_COOLANT_TEMPERATURE(1, 0x05, "°C"),
    RPM(1, 0x0C, "rpm"),
    SPEED(1, 0x0D, "km/h"),
    INTAKE_AIR_TEMPERATURE(1, 0x0F, "°C"),
    MAF_AIR_FLOW_RATE(1, 0x10, "g/s"),
    MAF_AIR_FLOW_RATE_SENSOR(1, 0x66, "g/s"),
    OXYGEN_SENSOR_1(1, 0x24, "LAMBDA | V"),
    COMMANDED_EGR(1, 0x2C, "%"),
    FUEL_TANK_LEVEL_INPUT(1, 0x2F, "%"),
    CATALYST_TEMPERATURE_1_1(1, 0x3C, "°C"),
    CATALYST_TEMPERATURE_1_2(1, 0x3D, "°C"),
    CATALYST_TEMPERATURE_2_1(1, 0x3E, "°C"),
    CATALYST_TEMPERATURE_2_2(1, 0x3F, "°C"),
    FUEL_AIR_EQUIVALENCE_RATIO(1, 0x44, "LAMBDA"),
    AMBIENT_AIR_TEMPERATURE(1, 0x46, "°C"),
    MAX_VALUES(1, 0x4F, "LAMBDA | V | mA | kPa"),
    MAXIMUM_AIR_FLOW_RATE(1, 0x50, "g/s"),
    FUEL_TYPE(1, 0x51, "Type"),
    ENGINE_OIL_TEMPERATURE(1, 0x5C, "°C"),
    INTAKE_AIR_TEMPERATURE_SENSOR(1, 0x68, "°C"),
    NOX_SENSOR(1, 0x83, "ppm"),
    NOX_SENSOR_CORRECTED(1, 0xA1, "ppm"),
    NOX_SENSOR_ALTERNATIVE(1, 0xA7, "ppm"),
    NOX_SENSOR_CORRECTED_ALTERNATIVE(1, 0xA8, "ppm"),
    PARTICULAR_MATTER_SENSOR(1, 0x86, "mg/m3"),
    ENGINE_FUEL_RATE(1, 0x5E, "L/h"),
    ENGINE_FUEL_RATE_MULTI(1, 0x9D, "g/s"),
    ENGINE_EXHAUST_FLOW_RATE(1, 0x9E, "kg/h"),
    EGR_ERROR(1, 0x2D, "%"),
    VIN(9, 0x02, ""),
    SUPPORTED_PIDS_1_00(1, 0x00, ""),
    SUPPORTED_PIDS_1_32(1, 0x20, ""),
    SUPPORTED_PIDS_1_64(1, 0x40, ""),
    SUPPORTED_PIDS_1_96(1, 0x60, ""),
    SUPPORTED_PIDS_1_128(1, 0x80, ""),
    SUPPORTED_PIDS_1_160(1, 0xA0, ""),
    SUPPORTED_PIDS_1_192(1, 0xC0, ""),
    SUPPORTED_PIDS_9_00(9, 0x00, "");

    companion object {
        /**
         *  Finds matching [OBDCommand] for given mode and pid.
         */
        fun getCommand(mode: Int, pid: Int): OBDCommand? {
            return try {
                values().first { it.mode == mode && it.pid == pid }
            } catch (e: Exception) {
                null
            }
        }
    }
}
