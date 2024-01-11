package com.example.composechat.utils

fun interface QueryMatcher {

    fun doesMatchSearchQuery(query: String): Boolean
}