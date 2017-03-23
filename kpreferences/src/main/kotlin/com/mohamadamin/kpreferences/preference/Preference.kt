package com.mohamadamin.kpreferences.preference

import android.content.SharedPreferences
import android.text.TextUtils
import com.mohamadamin.kpreferences.base.Adapter
import com.mohamadamin.kpreferences.base.KPreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/15/17.
 * A preference which can get and set the shared preference value. Uses the shared preferences object of
 * [com.mohamadamin.kpreferences.base.KPreferenceManager] instance.
 * @param default the default value of the shared preference
 * @param adapter the adapter used for converting other java objects to string so that they can be saved and
 * restored via shared preferences
 * @param name the name of the shared preference
 */
public open class Preference<T : Any?>(
        val default: T, var name: String = "", val adapter: Adapter<T>? = null) : ReadWriteProperty<Any?, T> {

    val prefs: SharedPreferences = KPreferenceManager.instance.prefs

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (TextUtils.isEmpty(name)) {
            name = "${property.name}KPreference"
        }
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (TextUtils.isEmpty(name)) {
            name = "${property.name}KPreference"
        }
        putPreference(name, value)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun findPreference(name: String, default: T): T = with(prefs) {
        return when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> {
                if (adapter != null) {
                    val result = adapter.decode(getString(name, ""))
                    if (result == null) default else result
                } else {
                    throw IllegalArgumentException("This type cannot be saved to preferences without an adapter")
                }
            }
        } as T
    }

    protected fun putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> {
                if (adapter != null) {
                    putString(name, adapter.encode(value))
                } else {
                    throw IllegalArgumentException("This type cannot be saved to preferences without an adapter")
                }
            }
        }.apply()
    }

}