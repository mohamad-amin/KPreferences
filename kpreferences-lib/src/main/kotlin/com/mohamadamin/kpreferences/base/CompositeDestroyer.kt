package com.mohamadamin.kpreferences.base

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 * A group of destroy listeners can be invoked all together
 */
class CompositeDestroyer {

    private val destroyListeners: MutableCollection<() -> Unit> = mutableSetOf()
    private var invoked = false

    /**
     * Add a new destroy listener to the group
     * @param listener the destroy listener that is going to be added in the group
     */
    fun add(listener: () -> Unit) = destroyListeners.add(listener)

    /**
     * Invoke all destroy listeners
     * **Note:** every group can be invoked **only once**.
     */
    fun invoke() {
        if (invoked) {
            throw IllegalStateException("This group is already invoked once")
        }
        destroyListeners.forEach { it.invoke() }
        invoked = true
    }

}