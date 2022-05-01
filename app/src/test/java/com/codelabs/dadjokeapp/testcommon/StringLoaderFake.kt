package com.codelabs.dadjokeapp.testcommon

import com.codelabs.dadjokeapp.common.StringLoader
import java.util.*

class StringLoaderFake : StringLoader {
    private val fakeStrings = mutableMapOf<Int, String>()

    override fun get(resourceId: Int): String {
        return fakeStrings[resourceId] ?: throw StringLoaderFakeNotPreparedException(resourceId)
    }

    fun expect(stringResValue: StringResValue) {
        fakeStrings[stringResValue.resId] = stringResValue.value
    }

}

data class StringResValue(
    val resId: Int,
    val value: String = UUID.randomUUID().toString()
)

class StringLoaderFakeNotPreparedException(resourceId: Int) : Exception(
    "Tried to retrieve string with resource ID: $resourceId, but was not available."
)