package com.mohamadamin.kpreferencesdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mohamadamin.kpreferencesdemo.base.OnNextScreenCalled

class MainActivity : AppCompatActivity(), OnNextScreenCalled {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val preferenceFragment = SettingsFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, preferenceFragment)
                    .commit()
        }

    }

    override fun onNextScreenCalled() {
        val resultFragment = ResultFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, resultFragment)
                .addToBackStack(resultFragment.javaClass.simpleName)
                .commit()
    }

}
