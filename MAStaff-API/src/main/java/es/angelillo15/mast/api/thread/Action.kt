package es.angelillo15.mast.api.thread

data class Action (val runnable: Runnable, val delay: Int, val repeat: Boolean, var delayTask: Int = delay)
