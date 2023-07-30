package es.angelillo15.mast.api.templates

data class WarnTemplate(
    val id: String,
    val warnReason: String,
    val warnMessage: String,
    val maxWarnings: String,
    val actions: List<WarnAction>,
    val deleteOnMax: Boolean,
    val permission: String
)
