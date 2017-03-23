package com.mohamadamin.kpreferencesdemo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.SwitchPreferenceCompat
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.mohamadamin.kpreferences.base.CompositeDestroyer
import com.mohamadamin.kpreferences.base.Subscriber
import com.mohamadamin.kpreferences.preference.ObservablePreference
import com.mohamadamin.kpreferences.preference.Preference
import com.mohamadamin.kpreferencesdemo.base.DummyAdapter
import com.mohamadamin.kpreferencesdemo.base.OnNextScreenCalled
import com.mohamadamin.kpreferencesdemo.base.Preferences

class SettingsFragment : PreferenceFragmentCompat() {

    var onNextScreenCalledListener : OnNextScreenCalled? = null

    val destroyers = CompositeDestroyer()
    var showNotification: Int by ObservablePreference(

            Preferences.showNotification.second,
            Preferences.showNotification.first,
            subscriber = object: Subscriber<Int> {

                override val subscriber: (Int) -> Unit
                    get() = {
                        setTitle("Reactive Preference Count: $it")
                    }

                override fun setDestroyListener(callback: () -> Unit) {
                    destroyers.add(callback)
                }

            }

    )

    var switchPref by Preference(Preferences.switch.second, Preferences.switch.first)
    var stringPref by Preference(Preferences.string.second, Preferences.string.first)
    var floatPref by Preference(Preferences.float.second, Preferences.float.first)
    var dummyObject by Preference(Preferences.dummy.second, Preferences.dummy.first, DummyAdapter())

    private fun initializePreferences() {

        val switchView = preferenceScreen.findPreference("booleanKey") as SwitchPreferenceCompat
        val stringView = preferenceScreen.findPreference("stringKey") as EditTextPreference
        val floatView = preferenceScreen.findPreference("floatKey") as EditTextPreference
        val dummyCheckView = preferenceScreen.findPreference("unusedCheck") as CheckBoxPreference
        val dummyTextView = preferenceScreen.findPreference("unusedEdit") as EditTextPreference

        switchView.setOnPreferenceChangeListener { preference, any ->
            switchPref = any as Boolean
            true
        }

        stringView.setOnPreferenceChangeListener { preference, any ->
            stringPref = any as String
            true
        }

        floatView.setOnPreferenceChangeListener { preference, any ->
            floatPref = (any as String).toFloat()
            true
        }

        dummyCheckView.setOnPreferenceChangeListener { preference, any ->
            dummyObject.checked = any as Boolean
            true
        }

        dummyTextView.setOnPreferenceChangeListener { preference, any ->
            dummyObject.text = any as String
            true
        }

        preferenceScreen.findPreference("nextScreen").setOnPreferenceClickListener {
            onNextScreenCalledListener?.onNextScreenCalled()
            true
        }

    }

    private fun setTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.subtitle = title
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preference)
        setHasOptionsMenu(true)
        initializePreferences()
        showNotification = showNotification+1
        showNotification = showNotification-1
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_add -> {
                showNotification++
                true
            }
            R.id.menu_subtract -> {
                showNotification--
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onNextScreenCalledListener = if (context is OnNextScreenCalled) context else null
    }

    override fun onDetach() {
        super.onDetach()
        onNextScreenCalledListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyers.invoke()
    }

}