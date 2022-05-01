package com.codelabs.dadjokeapp.common

import android.content.Context

interface StringLoader {

    fun get(resourceId: Int): String

    companion object {
        operator fun invoke(context: Context): StringLoader {
            return object : StringLoader {
                override fun get(resourceId: Int): String {
                    return context.getString(resourceId)
                }
            }
        }
    }

}