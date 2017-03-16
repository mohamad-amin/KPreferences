package com.mohamadamin.kpreferences.base

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 * A subscriber to implement observable feature to get updates when a [android.content.SharedPreferences] changes,
 * helps reduce boilerplate for writing
 * [android.content.SharedPreferences.registerOnSharedPreferenceChangeListener] and
 * [android.content.SharedPreferences.unregisterOnSharedPreferenceChangeListener]
 */
interface Subscriber<T> {

    /**
     * Get the callback for getting updates when [android.content.SharedPreferences] changes
     * **Note:** The [android.content.SharedPreferences.OnSharedPreferenceChangeListener] holds a strong reference
     * to the context it is built upon (usually your application class) and you should call the callback that
     * comes from [setDestroyListener] after you finish working with it
     * (in the activity's onDestroy for example) to avoid possible memory leaks.
     * @return the callback for getting updates
     */
    val subscriber: (T) -> Unit

    /**
     * Used for unregistering the [android.content.SharedPreferences.OnSharedPreferenceChangeListener]
     * when you don't need the preference anymore.
     * @param callback the callback that you should invoke when you want to unregister the
     * [android.content.SharedPreferences.OnSharedPreferenceChangeListener] (usually in activity's onDestroy)
     */
    fun setDestroyListener(callback: () -> Unit)

}