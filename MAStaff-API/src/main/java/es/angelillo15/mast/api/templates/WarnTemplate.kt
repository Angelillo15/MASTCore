package es.angelillo15.mast.api.templates

data class WarnTemplate(
        val id: String,
        val warnDuration: Long,
        val warnReason: String,
        val warnMessage: String,
        val maxWarnings: Int,
        val actions: List<WarnAction?>,
        val deleteOnMax: Boolean,
        val permission: String
)
