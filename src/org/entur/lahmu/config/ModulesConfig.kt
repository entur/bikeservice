package org.entur.lahmu.config

import io.ktor.client.HttpClient
import org.entur.lahmu.domain.service.BikeService
import org.entur.lahmu.domain.service.BikeServiceImpl
import org.entur.lahmu.domain.service.Cache
import org.entur.lahmu.domain.service.InMemoryCache
import org.entur.lahmu.web.controllers.BikesController
import org.entur.lahmu.web.controllers.BikesControllerImpl
import org.koin.dsl.module

val modulesConfig = module {
    single<BikeService> { BikeServiceImpl(HttpClient()) }
    single<Cache> { InMemoryCache(HashMap()) }
    single<BikesController> { (bikeService: BikeService, cache: Cache) -> BikesControllerImpl(bikeService, cache) }
}
