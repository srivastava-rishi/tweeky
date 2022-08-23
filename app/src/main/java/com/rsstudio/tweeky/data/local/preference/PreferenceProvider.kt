package com.rsstudio.tweeky.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceProvider
@Inject
constructor(
    @ApplicationContext private val context: Context
){


    companion object {

        const val SORT_TYPE = "sort_type"

        // default value
        const val DFT_SORT_TYPE : Int = 1

    }

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    fun clearPreferences() {
        preference.edit().clear().apply()
    }


    fun saveSortType(sort_type: Int) {
        preference.edit().putInt(
            SORT_TYPE,
            sort_type
        ).apply()
    }

    fun getSortType(): Int {
        return preference.getInt(SORT_TYPE, DFT_SORT_TYPE)
    }


}