package es.angelillo15.mast.api.thread

import es.angelillo15.mast.api.MAStaffInstance
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

private const val tps = 5
private const val miles = 1000 / tps
private var shuttingDown = false
private var thread: Unit? = null

private var actions = ArrayList<Action>()
private var threadPoolExecutor = Executors.newFixedThreadPool(5)

@Suppress("UNCHECKED_CAST")
fun start() {
  Thread({
    MAStaffInstance.getLogger().debug("Starting parallel thread...")

    while (!shuttingDown) {
      try {
        Thread.sleep(200)
      } catch (e: InterruptedException) {
        MAStaffInstance.getLogger().error("Error while sleeping thread: ${e.message}")
      }

      if (shuttingDown) {
        break
      }

      val actionsClone: ArrayList<Action> = getActions().clone() as ArrayList<Action>

      for (action in actionsClone) {
        if (action.delayTask > 0) {
          action.delayTask -= miles
          continue
        } else {
          action.delayTask = action.delay
        }

        threadPoolExecutor.execute {
          try {
            action.runnable.invoke()
            MAStaffInstance.getLogger().debug("Executed action $action")
          } catch (e: Exception) {
            MAStaffInstance.getLogger().error("Error while executing action ${action}: ${e.message}")
          }
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
fun getActions(): ArrayList<Action> {
  return actions
}

/**
 * Get the current thread
 * @return The thread
 */
fun getThread(): Unit? {
  return thread
}

/**
 * Executes a runnable
 * @param runnable The runnable to execute
 * @param delay The delay in milliseconds
 * @param repeat If the action should repeat
 */
fun execute(runnable: () -> Unit, delay: Int?, repeat: Boolean?): Int {
  return addAction(Action(runnable, delay ?: 0, repeat ?: false))
}

/**
 * Executes a runnable
 * @param runnable The runnable to execute
 */
fun execute(runnable: () -> Unit): Int {
  return execute(runnable, 0, false)
}

object AsyncThread {
  /**
   * Adjusts the threads
   * @param threads The amount of threads
   */
  fun adjustThreads(threads: Int) {
    getThreadPoolExecutor().corePoolSize = threads
  }

  /**
   * Get the current thread pool executor
   * @return The thread pool executor
   */
  fun getThreadPoolExecutor(): ThreadPoolExecutor {
    return threadPoolExecutor as ThreadPoolExecutor
  }

  /**
   * Increment the threads by 1
   */
  fun incrementThreads() {
    incrementThreads(1)
  }

  /**
   * Decrement the threads by 1
   */
  fun decrementThreads() {
    decrementThreads(1)
  }

  /**
   * Increment the threads by an amount
   * @param amount The amount to increment
   */
  fun incrementThreads(amount: Int) {
    adjustThreads(getThreadPoolExecutor().corePoolSize + amount)
  }

  /**
   * Decrement the threads by an amount
   * @param amount The amount to decrement
   */
  fun decrementThreads(amount: Int) {
    adjustThreads(getThreadPoolExecutor().corePoolSize - amount)
  }
}