package com.mohamadamin.kpreferencesdemo.base

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 */
object Preferences {
    val showNotification = "showNotification" to 0
    val switch = "switchPreference" to true
    val string = "stringPreference" to "Default String"
    val float = "doublePreference" to 3.14f
    val dummy = "dummyPreference" to DummyClass(true, "Default Dummy")
}