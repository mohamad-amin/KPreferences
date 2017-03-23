package com.mohamadamin.kpreferences.preference

import android.content.SharedPreferences
import com.mohamadamin.kpreferences.base.Adapter
import com.mohamadamin.kpreferences.base.Subscriber

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 * An Observable preference which can be subscribed to get updates when the preference changes
 * or it can get and set the shared preference value. Uses the shared preferences object of
 * [com.mohamadamin.kpreferences.base.KPreferenceManager] instance.
 * @param default the default value of the shared preference
 * @param adapter the adapter used for converting other java objects to string so that they can be saved and
 * restored via shared preferences
 * @param name the name of the shared preference
 * @param subscriber the function used for notifying shared preference changes for this #name
 */
class ObservablePreference<T: Any>(default: T, name: String, adapter: Adapter<T>? = null,
                                   subscriber: Subscriber<T>) : Preference<T>(default, name, adapter) {

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key.equals(name)) {
            subscriber.subscriber.invoke(findPreference(name, default))
        }
    }

    init {
        prefs.registerOnSharedPreferenceChangeListener(listener)
        subscriber.setDestroyListener {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }


}