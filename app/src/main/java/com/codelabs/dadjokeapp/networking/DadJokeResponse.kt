package com.codelabs.dadjokeapp.networking

import com.squareup.moshi.Json

data class DadJokeResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "joke") val joke: String,
    @field:Json(name = "status") val status: Int
)
