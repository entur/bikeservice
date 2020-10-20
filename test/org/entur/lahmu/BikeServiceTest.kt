package org.entur.lahmu

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.entur.lahmu.domain.GBFSResponse
import org.entur.lahmu.domain.GbfsStandardEnum
import org.entur.lahmu.domain.bikeOperators.Operator
import org.entur.lahmu.domain.service.BikeServiceImpl
import org.entur.lahmu.domain.service.InMemoryCache
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BikeServiceTest {

    @ExperimentalCoroutinesApi
    @Test
    fun testPolling() = runBlocking {
        val bikeService = BikeServiceImpl(HttpMockEngine().client)
        val cache = InMemoryCache(HashMap())

        assertNull(cache.getResponseFromCache(
            Operator.OSLOBYSYKKEL,
            GbfsStandardEnum.station_status
        ))

        bikeService.poll(cache).join()

        val response = cache.getResponseFromCache(
            Operator.OSLOBYSYKKEL,
            GbfsStandardEnum.station_status
        ) as GBFSResponse.StationStatusesResponse

        assertEquals("YOS:VehicleSharingParkingArea:1919", response.data.stations[0].stationId)
    }
}
