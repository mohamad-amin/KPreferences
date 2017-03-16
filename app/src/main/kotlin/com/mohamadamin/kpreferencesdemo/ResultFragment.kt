package com.mohamadamin.kpreferencesdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.mohamadamin.kpreferences.preference.Preference
import com.mohamadamin.kpreferencesdemo.base.DummyAdapter
import com.mohamadamin.kpreferencesdemo.base.Preferences

class ResultFragment : Fragment() {

    @BindView(R.id.switchPref)
    lateinit var switchText: TextView
    @BindView(R.id.stringPref)
    lateinit  var stringText: TextView
    @BindView(R.id.floatPref)
    lateinit var floatText: TextView
    @BindView(R.id.dummyPref)
    lateinit var dummyText: TextView
    lateinit var unbinder: Unbinder

    var switchPref by Preference(Preferences.switch.second, Preferences.switch.first)
    var stringPref by Preference(Preferences.string.second, Preferences.string.first)
    var floatPref by Preference(Preferences.float.second, Preferences.float.first)
    var dummyObject by Preference(Preferences.dummy.second, Preferences.dummy.first, DummyAdapter())

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater!!.inflate(R.layout.fragment_result, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchText.text = "SwitchPref is: $switchPref"
        stringText.text = "StringPref is: $stringPref"
        floatText.text = "DoublePref is: $floatPref"
        dummyText.text = "DummyPref (custom object) is: $dummyObject"

    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }

}
