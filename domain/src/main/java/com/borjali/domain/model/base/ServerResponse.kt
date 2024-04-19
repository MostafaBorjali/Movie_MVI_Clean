@file:Suppress("unused")

package com.borjali.domain.model.base

import com.google.gson.annotations.SerializedName

class ServerResponse<T>(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: T? = null,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)
