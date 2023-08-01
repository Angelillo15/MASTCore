package es.angelillo15.mast.api.thread

import es.angelillo15.mast.api.MAStaffInstance
import es.angelillo15.mast.api.utils.MAStaffInject

private const val tps = 5
private const val miles = 1000 / tps
private var shuttingDown = false
private var thread: Unit? = null

private var actions = ArrayList<Action>()

@Suppress("UNCHECKED_CAST")
fun start() {
    Thread({
        MAStaffInstance.getLogger().debug("Starting parallel thread...")

        while (!shuttingDown) {
            try {
                Thread.sleep(200)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            if (shuttingDown) {
                break
            }

            val actionsClone:ArrayList<Action> = getActions().clone() as ArrayList<Action>

            for (action in actionsClone) {
                if (action.delayTask > 0) {
                    action.delayTask -= miles
                    continue
                } else {
                    action.delayTask = action.delay
                }

                try {
                    action.runnable.invoke()
                    MAStaffInstance.getLogger().debug("Executed action $action")
                } catch (e: Exception) {
                    MAStaffInstance.getLogger().error("Error while executing action ${action}: ${e.message}")
                }

                if (!action.repeat) removeAction(action)
            }
        }



        shuttingDown = false
        MAStaffInstance.getLogger().debug("Parallel thread stopped!")
    }, "MAStaff-ParallelThread").start()
}

/**
 * Stops the thread
 */
fun stop() {
    MAStaffInstance.getLogger().debug("Stopping parallel thread...")
    shuttingDown = true
    clearActions()
}


/**
 * Adds an action to the thread
 * @param action The action to add
 */
fun addAction(action: Action): Int {
    actions.add(action)
    return actions.indexOf(action)
}

/**
 * Removes an action from the thread
 * @param action The action to remove
 */
fun removeAction(action: Action) {
    actions.remove(action)
}

/**
 * Removes an action from the thread
 * @param index The index of the action to remove
 */
fun removeAction(index: Int) {
    actions.removeAt(index)
}

/**
 * Removes all actions from the thread
 */
fun clearActions() {
    actions.clear()
}

/**
 * Get all current actions
 * @return The actions
 */
fun getActions() : ArrayList<Action> {
    return actions
}

/**
 * Get the current thread
 * @return The thread
 */
fun getThread() : Unit? {
    return thread
}

/**
 * Executes a runnable
 * @param runnable The runnable to execute
 * @param delay The delay in milliseconds
 * @param repeat If the action should repeat
 */
fun execute(runnable: () -> Unit, delay: Int?, repeat: Boolean?) : Int {
    return addAction(Action(runnable, delay ?: 0, repeat ?: false))
}

/**
 * Executes a runnable
 * @param runnable The runnable to execute
 */
fun execute(runnable: () -> Unit) : Int {
    return execute(runnable, 0, false)
}