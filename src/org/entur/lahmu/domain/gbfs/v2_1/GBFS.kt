package org.entur.lahmu.domain.gbfs.v2_1

import kotlinx.serialization.*

@Serializable
data class GBFS(
    @Required @SerialName("last_updated") override val lastUpdated: Long,
    @Required override val ttl: Int,
    @Required override val version: String,
    @Required override val data: Map<String, GBFSData>
) : GBFSBase() {
    init {
        validate()
    }
}

@Serializable
data class GBFSData(
    @Required val feeds: List<Feed>
)

@Serializable
data class Feed(
    @Required val name: GBFSFeedName,
    @Required val url: String
)