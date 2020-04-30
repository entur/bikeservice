package org.entur

import com.google.gson.Gson
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import java.lang.NullPointerException
import java.time.LocalDateTime

fun main() {
    val server = embeddedServer(Jetty, watchPaths = listOf("bikeservice"), port = 8080, module = Application::module)
    server.start(wait = true)
}

fun Application.module() {
    val gbfsCache = InMemoryCache(HashMap(), LocalDateTime.now())
    val systemInformationCache = InMemoryCache(HashMap(), LocalDateTime.now())
    val stationInformationCache = InMemoryCache(HashMap(), LocalDateTime.now())
    val stationStatusCache = InMemoryCache(HashMap(), LocalDateTime.now())

    routing {
        get("/") {
            call.respondText("Hello and welcome to Entur Bikeservice!", ContentType.Application.Json)
        }
        get("{operator}/gbfs.json") {
            val operator = BikeOperator.valueOf(call.parameters["operator"]?.toUpperCase() ?: throw NullPointerException())
            val result = if (gbfsCache.isValidCache(operator)) {
                gbfsCache.getResponseFromCache(operator)
            } else {
                val response = parseResponse<BikeResponse>(getOperatorGbfs(operator).gbfs)
                gbfsCache.setResponseInCache(operator, response)
                response
            }
            call.respondText(Gson().toJson(result), ContentType.Application.Json)
        }
        get("{operator}/system_information.json") {
            val operator = BikeOperator.valueOf(call.parameters["operator"]?.toUpperCase() ?: throw NullPointerException())
            val result = if (systemInformationCache.isValidCache(operator)) {
                systemInformationCache.getResponseFromCache(operator)
            } else {
                val response = parseResponse<BikeResponse>(getOperatorGbfs(operator).gbfs)
                systemInformationCache.setResponseInCache(operator, response)
                response
            }
            call.respondText(Gson().toJson(parseResponse<SystemInformationResponse>(getOperatorGbfs(operator).system_information)), ContentType.Application.Json)
        }
        get("{operator}/station_information.json") {
            val operator = BikeOperator.valueOf(call.parameters["operator"]?.toUpperCase() ?: throw NullPointerException())
            val result = if (stationInformationCache.isValidCache(operator)) {
                stationInformationCache.getResponseFromCache(operator)
            } else {
                val response = parseResponse<BikeResponse>(getOperatorGbfs(operator).gbfs)
                stationInformationCache.setResponseInCache(operator, response)
                response
            }
            call.respondText(Gson().toJson(parseResponse<StationInformationResponse>(getOperatorGbfs(operator).station_information)), ContentType.Application.Json)
        }
        get("{operator}/station_status.json") {
            val operator = BikeOperator.valueOf(call.parameters["operator"]?.toUpperCase() ?: throw NullPointerException())
            val result = if (stationStatusCache.isValidCache(operator)) {
                stationStatusCache.getResponseFromCache(operator)
            } else {
                val response = parseResponse<BikeResponse>(getOperatorGbfs(operator).gbfs)
                stationStatusCache.setResponseInCache(operator, response)
                response
            }
            call.respondText(Gson().toJson(parseResponse<StationStatusResponse>(getOperatorGbfs(operator).station_status)), ContentType.Application.Json)
        }
        get("/all") {
            call.respondText(Gson().toJson(getAllOperatorsWithGbfs()), ContentType.Application.Json)
        }
    }
}

suspend inline fun <reified T> parseResponse(url: String): T {
    with(HttpClient()) {
        val response = get<String>(url) { header("Client-Identifier", "entur-bikeservice") }
        return Gson().fromJson(response, T::class.java)
    }
}
