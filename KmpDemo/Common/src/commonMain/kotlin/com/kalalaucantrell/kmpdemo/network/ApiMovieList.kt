package com.kalalaucantrell.kmpdemo.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiMovieList(val count: Int, val results: List<ApiMovie>)
