package org.entur.lahmu.domain.bikeOperators

import org.entur.lahmu.config.epochOf31Dec2020
import org.entur.lahmu.config.epochOf5thJune2020
import org.entur.lahmu.domain.GBFSResponse
import org.entur.lahmu.domain.GbfsStandardEnum
import org.entur.lahmu.domain.SystemPricePlan

val osloBysykkelURL = mapOf(
    GbfsStandardEnum.gbfs to "https://gbfs.urbansharing.com/oslobysykkel.no/gbfs.json",
    GbfsStandardEnum.system_information to "https://gbfs.urbansharing.com/oslobysykkel.no/system_information.json",
    GbfsStandardEnum.station_information to "https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json",
    GbfsStandardEnum.station_status to "https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json",
    GbfsStandardEnum.system_pricing_plans to ""
)
val bergenBysykkelURL = mapOf(
    GbfsStandardEnum.gbfs to "https://gbfs.urbansharing.com/bergenbysykkel.no/gbfs.json",
    GbfsStandardEnum.system_information to "https://gbfs.urbansharing.com/bergenbysykkel.no/system_information.json",
    GbfsStandardEnum.station_information to "https://gbfs.urbansharing.com/bergenbysykkel.no/station_information.json",
    GbfsStandardEnum.station_status to "https://gbfs.urbansharing.com/bergenbysykkel.no/station_status.json",
    GbfsStandardEnum.system_pricing_plans to ""
)

val trondheimBysykkelURL = mapOf(
    GbfsStandardEnum.gbfs to "https://gbfs.urbansharing.com/trondheimbysykkel.no/gbfs.json",
    GbfsStandardEnum.system_information to "https://gbfs.urbansharing.com/trondheimbysykkel.no/system_information.json",
    GbfsStandardEnum.station_information to "https://gbfs.urbansharing.com/trondheimbysykkel.no/station_information.json",
    GbfsStandardEnum.station_status to "https://gbfs.urbansharing.com/trondheimbysykkel.no/station_status.json",
    GbfsStandardEnum.system_pricing_plans to ""
)

fun urbanSharingSystemPricePlan(operator: Operator): GBFSResponse.SystemPricingPlans =
    GBFSResponse.SystemPricingPlans(
        lastUpdated = epochOf5thJune2020,
        ttl = getSecondsFrom(epochOf5thJune2020, epochOf31Dec2020),
        plans = listOf(
            SystemPricePlan(
                planId = "CD863B56-B502-4FDE-B872-C21CD1F8F15C",
                url = when (operator) {
                    Operator.OSLOBYSYKKEL -> "https://oslobysykkel.no/"
                    Operator.BERGENBYSYKKEL -> "https://bergenbysykkel.no/"
                    Operator.TRONDHEIMBYSYKKEL -> "https://trondheimbysykkel.no/"
                    else -> ""
                },
                name = PricePlan.SEASON_PASS.toString(),
                currency = "NOK",
                price = 399.0,
                isTaxable = 0,
                description = "Ved sammenhengende bruk i over en time vil det forekomme et ekstra gebyr på 15.0 NOK per kvarter."
            ),
            SystemPricePlan(
                planId = "3F6450C4-05F7-4E4E-8E71-2E641E011FEE",
                url = when (operator) {
                    Operator.OSLOBYSYKKEL -> "https://oslobysykkel.no/"
                    Operator.BERGENBYSYKKEL -> "https://bergenbysykkel.no/"
                    Operator.TRONDHEIMBYSYKKEL -> "https://trondheimbysykkel.no/"
                    else -> ""
                },
                name = PricePlan.DAY_PASS_1.toString(),
                currency = "NOK",
                price = 49.0,
                isTaxable = 0,
                description = "Ved sammenhengende bruk i over en time, vil det forekomme et ekstra gebyr på 15.0 NOK per kvarter."
            )
        )
    )
