package com.deezerapp.helpers

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal fun encodeForSafe(string: String): String =
    URLEncoder.encode(string, StandardCharsets.UTF_8.toString())