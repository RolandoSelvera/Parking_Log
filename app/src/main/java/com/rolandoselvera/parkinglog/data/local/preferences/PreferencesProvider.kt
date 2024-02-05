package com.rolandoselvera.parkinglog.data.local.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.rolandoselvera.parkinglog.data.models.CarEntity
import com.rolandoselvera.parkinglog.utils.deserialize
import com.rolandoselvera.parkinglog.utils.serialize

class PreferencesProvider {
    companion object {
        private const val FIELDS_PREF_KEY = "FIELDS_PREF_KEY"
        private lateinit var preferences: SharedPreferences

        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun init(context: Context) {
            this.context = context
            preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }

        private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
            val editor = edit()
            operation(editor)
            editor.apply()
        }

        fun removePreferences() {
            preferences.edit {
                it.remove(FIELDS_PREF_KEY)
            }
        }

        var CAR_ENTITY
            get() = preferences.getString(FIELDS_PREF_KEY, null)?.let {
                deserialize(it, CarEntity::class.java)
            }
            set(value) {
                val json = serialize(value)
                preferences.edit {
                    it.putString(FIELDS_PREF_KEY, json).apply()
                }
            }
    }
}
