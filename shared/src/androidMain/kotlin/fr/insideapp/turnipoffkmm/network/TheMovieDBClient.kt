package fr.insideapp.turnipoffkmm.network

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

actual val defaultPlatformEngine: HttpClientEngine = Android.create()