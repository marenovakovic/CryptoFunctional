package com.marko.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {

	val prefs: SharedPreferences

	val lastCacheTime: Long
}

private class LongPreference(
	private val preferences: SharedPreferences,
	val name: String,
	val defaultValue: Long
) : ReadWriteProperty<Any, Long> {

	override fun getValue(thisRef: Any, property: KProperty<*>): Long =
		preferences.getLong(name, defaultValue)

	override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) =
		preferences.edit { putLong(name, value) }
}