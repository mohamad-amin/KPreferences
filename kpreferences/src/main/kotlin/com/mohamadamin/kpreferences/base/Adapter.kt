package com.mohamadamin.kpreferences.base

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 3/16/17.
 * Converter for storing and retrieving objects of type [T] in [android.content.SharedPreferences]
 */
interface Adapter<T> {

    /**
     * Decode the string retrieved from [android.content.SharedPreferences] to an object of type [T]
     * @param result the string retrieved from {@link SharedPreferences}
     * @return the decoded object of type [T]
     */
    fun decode(result: String): T?

    /**
     * Encode an object of type [T] to a string that can be saved in [android.content.SharedPreferences]
     * @param value the object of type [T] that wants to be saved in [android.content.SharedPreferences]
     * @return the encoded string from the input #value that can be saved in [android.content.SharedPreferences]
     */
    fun encode(value: T): String

}