package org.entur.mobility.bikes.bikeOperators

import org.entur.mobility.bikes.GbfsStandard
import org.entur.mobility.bikes.getGbfsEndpoint

enum class Operators {
    OSLOBYSYKKEL, BERGENBYSYKKEL, TRONDHEIMBYSYKKEL, KOLUMBUSBYSYKKEL;

    companion object {
        fun isUrbanSharing(operators: Operators) = operators !== KOLUMBUSBYSYKKEL
        fun getCodeSpace(operator: Operators) = when (operator) {
            OSLOBYSYKKEL -> "YOS"
            BERGENBYSYKKEL -> "YBE"
            TRONDHEIMBYSYKKEL -> "YTR"
            KOLUMBUSBYSYKKEL -> "YKO"
        }
    }
}

fun getOperatorsWithDiscovery(port: String, host: Int): Map<String, List<Map<String, String>>> =
    mapOf("operators" to Operators.values().map {
        mapOf("$it".toLowerCase() to getGbfsEndpoint(it, port, host).gbfs)
    })

fun getOperator(operator: Operators): GbfsStandard =
    when (operator) {
        Operators.OSLOBYSYKKEL -> osloBysykkelURL
        Operators.BERGENBYSYKKEL -> bergenBysykkelURL
        Operators.TRONDHEIMBYSYKKEL -> trondheimBysykkelURL
        Operators.KOLUMBUSBYSYKKEL -> kolumbusBysykkelURL
    }

fun mapIdToNeTEx(id: String, operator: Operators) = "${Operators.getCodeSpace(operator)}:BikeStation:$id"
