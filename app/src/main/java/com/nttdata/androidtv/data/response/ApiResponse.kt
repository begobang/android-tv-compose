package com.nttdata.androidtv.data.response

data class ApiResponse<T>(
    val page: Int,
    val results: List<T>
)
