package com.example.giphyapp.network.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data") val data: List<Response>,
    @SerializedName("pagination") val pagination: Pagination
)

data class Pagination(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("count") val count: Int?,
    @SerializedName("offset") val offset: Int
)