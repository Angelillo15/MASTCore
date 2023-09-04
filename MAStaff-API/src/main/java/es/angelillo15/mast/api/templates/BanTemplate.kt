package es.angelillo15.mast.api.templates

data class BanTemplate(
        val id: String,
        val banDuration: Long,
        val banMessage: String,
        val permission: String,
        val ipBan: Boolean
)
