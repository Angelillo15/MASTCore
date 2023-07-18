package es.angelillo15.mast.api.thread

data class Action (val runnable: Runnable, var delay: Int, val repeat: Boolean)
