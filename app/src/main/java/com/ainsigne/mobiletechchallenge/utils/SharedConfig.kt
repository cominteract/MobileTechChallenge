package com.ainsigne.mobiletechchallenge.utils;

import android.content.SharedPreferences

/**
 * Config for saving preferences
 */
class SharedConfig {
    /**
     * Shared pref to be initialized as [SharedPreferences]
     */
    private var sharedPreferences: SharedPreferences? = null

    /**
     * Search key for saving searched queries
     */
    var searchKey = "searched"

    /**
     * @param sharedPreferences as [SharedPreferences] to be initialized
     */
    constructor(sharedPreferences: SharedPreferences){
        this.sharedPreferences = sharedPreferences
    }

    /**
     * saves the last searched query to return to its last state
     */
    fun saveLastSearched(searched : String?) {
        val editor = sharedPreferences?.edit()
        editor?.putString(searchKey, searched)
        editor?.apply()
    }

    /**
     * gets the last searched query to be queried when returning
     */
    fun getLastSearched() : String?{
        return sharedPreferences?.getString(searchKey, null)
    }




}