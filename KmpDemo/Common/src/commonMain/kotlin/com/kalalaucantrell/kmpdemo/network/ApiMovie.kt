package com.kalalaucantrell.kmpdemo.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiMovie(val title: String, val url: String)
