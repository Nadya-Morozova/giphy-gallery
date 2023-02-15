package com.example.giphyapp.network.data

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("original") val original: Original
)

data class Original(
    @SerializedName("url") val url: String?
)

