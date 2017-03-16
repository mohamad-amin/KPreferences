package com.mohamadamin.kpreferencesdemo.base

import com.google.gson.Gson
import com.mohamadamin.kpreferences.base.Adapter

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 */
class DummyAdapter : Adapter<DummyClass> {

    val gson by lazy { Gson() }

    override fun decode(result: String): DummyClass? {
        return gson.fromJson(result, DummyClass::class.java)
    }


    override fun encode(value: DummyClass): String =
            gson.toJson(value)!!

}