package com.mohamadamin.kpreferencesdemo.base

import android.app.Application
import com.mohamadamin.kpreferences.base.KPreferenceManager

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 */
class KPreferencesDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KPreferenceManager.initialize(this)
    }

}