package pcdfEvent

/**
 * Manages the used PCDF ID's.
 * Checks ID's for validity and uniqueness.
 */
class IDManager {
    private val usedIDs = mutableSetOf<String>()

    /**
     * Checks PCDF ID for validity and uniqueness.
     * @param id ID to check
     * @return Whether the ID is a valid PCDF ID and not used in the current instance.
     */
    fun checkID(id: String): Boolean {
        // possibly add validity check here
        return !usedIDs.contains(id)
    }

    fun used(id: String): Boolean {
        return !usedIDs.contains(id)
    }

    /**
     * Checks and adds new ID to the used ID's.
     * @param id ID to add.
     * @return Whether the ID was added successfully (valid and not already in use).
     */
    fun addID(id: String): Boolean {
        return if (checkID(id)) {
            usedIDs.add(id)
            true
        } else {
            false
        }
    }

    /**
     * Removes given ID from the used ID set.
     */
    fun removeID(id: String) {
        usedIDs.remove(id)
    }
}