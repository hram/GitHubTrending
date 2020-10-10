package hram.githubtrending.bot

import com.google.gson.annotations.SerializedName

data class Apps(
    @SerializedName("next")
    val next: String,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("appId")
        val appId: String,
        @SerializedName("developer")
        val developer: Developer,
        @SerializedName("developerId")
        val developerId: String,
        @SerializedName("free")
        val free: Boolean,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("permissions")
        val permissions: String,
        @SerializedName("playstoreUrl")
        val playstoreUrl: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("priceText")
        val priceText: String,
        @SerializedName("reviews")
        val reviews: String,
        @SerializedName("score")
        val score: Double,
        @SerializedName("scoreText")
        val scoreText: String,
        @SerializedName("similar")
        val similar: String,
        @SerializedName("summary")
        val summary: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String
    ) {
        data class Developer(
            @SerializedName("devId")
            val devId: String,
            @SerializedName("url")
            val url: String
        )
    }
}