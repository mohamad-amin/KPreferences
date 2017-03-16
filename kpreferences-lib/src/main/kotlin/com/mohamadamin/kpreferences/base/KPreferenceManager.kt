package com.mohamadamin.kpreferences.base

import android.content.Context

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 * The manager class for holding an instance of [android.content.SharedPreferences] and clearing it
 * using [clear] function
 */
class KPreferenceManager private constructor(private val context: Context,
                         private val name: String = "default", private val mode: Int = Context.MODE_PRIVATE) {

    companion object {

        var context: Context? = null
        var name: String = "default"
        var mode: Int = Context.MODE_PRIVATE

        /**
         * The initializing the [KPreferenceManager] singleton class
         * @param context the context used for creating the [android.content.SharedPreferences]
         * @param name the name of the shared preferences file (used in [Context.getSharedPreferences])
         * @param mode mode Operating mode. Use 0 or [Context.MODE_PRIVATE] for the
         * default operation. (used in [Context.getSharedPreferences])
         */
        fun initialize(context: Context, name: String = "default", mode: Int = Context.MODE_PRIVATE) {
            this.context = context
            this.name = name
            this.mode = mode
        }

        /**
         * Returns an instance of this singleton class
         */
        val instance by lazy {
            KPreferenceManager(context!!, name, mode)
        }

    }

    val prefs by lazy {
        context.getSharedPreferences(name, mode)
    }

    /**
     * Clearing all shared preferences form this {@link name} file of shared preferences
     */
    fun clear() = prefs.edit().clear().apply()

}