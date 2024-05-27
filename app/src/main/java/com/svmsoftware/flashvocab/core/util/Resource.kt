package com.svmsoftware.flashvocab.core.util

sealed class Resource<T>(val data: T? = null, val desc: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(desc: String, data: T? = null) : Resource<T>(data, desc)
}