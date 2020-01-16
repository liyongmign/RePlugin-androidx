package com.pluginapp

import android.content.Context
import android.util.Log
import android.widget.Toast

object Utils {
    private const val TAG = "Utils"
    fun toast(context: Context?, message: CharSequence?) {
        Toast.makeText(context?.applicationContext ?: return, message, Toast.LENGTH_SHORT).show()
    }

    fun log(tag: Any?, message: Any?) {
        Log.d(tag?.javaClass?.simpleName ?: TAG, "$message")
    }
}