package org.entur.lahmu

import java.time.LocalDateTime
import java.time.ZoneOffset
import org.entur.lahmu.bikeOperators.Operator

interface Cache {
    val cacheMap: HashMap<Operator, HashMap<GbfsStandardEnum, GBFSResponse>>

    fun isValidCache(bikeOperator: Operator, gbfsStandardEnum: GbfsStandardEnum): Boolean
    fun getResponseFromCache(bikeOperator: Operator, gbfsStandardEnum: GbfsStandardEnum): GBFSResponse?
    fun setResponseInCacheAndGet(operator: Operator, gbfsStandardEnum: GbfsStandardEnum, response: GBFSResponse): GBFSResponse
}

class InMemoryCache(
    override val cacheMap: HashMap<Operator, HashMap<GbfsStandardEnum, GBFSResponse>>
) : Cache {
    override fun getResponseFromCache(bikeOperator: Operator, gbfsStandardEnum: GbfsStandardEnum): GBFSResponse? {
        return cacheMap[bikeOperator]?.get(gbfsStandardEnum)
    }

    override fun setResponseInCacheAndGet(
        operator: Operator,
        gbfsStandardEnum: GbfsStandardEnum,
        response: GBFSResponse
    ): GBFSResponse {
        if (cacheMap[operator] == null) {
            cacheMap[operator] = hashMapOf(gbfsStandardEnum to response)
        } else {
            cacheMap[operator]!![gbfsStandardEnum] = response
        }
        return response
    }

    override fun isValidCache(bikeOperator: Operator, gbfsStandardEnum: GbfsStandardEnum): Boolean {
        val drammenCheck =
            if (bikeOperator == Operator.DRAMMENBYSYKKEL && gbfsStandardEnum == GbfsStandardEnum.station_information) {
                cacheCheck(cacheMap, bikeOperator, GbfsStandardEnum.station_status)
            } else true
        return cacheCheck(cacheMap, bikeOperator, gbfsStandardEnum) && drammenCheck
    }
}

fun cacheCheck(
    cacheMap: HashMap<Operator, HashMap<GbfsStandardEnum, GBFSResponse>>,
    bikeOperator: Operator,
    gbfsStandardEnum: GbfsStandardEnum
) =
    LocalDateTime.ofEpochSecond(
        cacheMap[bikeOperator]?.get(gbfsStandardEnum)?.lastUpdated ?: 0L,
        0,
        ZoneOffset.UTC
    ) > LocalDateTime.now().minusSeconds(TTL)