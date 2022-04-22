package fr.insideapp.turnipoffkmm.network

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual val defaultPlatformEngine: HttpClientEngine = Darwin.create()
