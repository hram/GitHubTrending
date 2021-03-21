package hram.githubtrending.bot

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Kosh on 02 Jun 2017, 12:58 PM
 */

interface ScrapService {

    @GET("{lan}")
    suspend fun getTrending(@Path("lan") lan: String?, @Query("since") since: String?): Response<String>

    @GET("api/apps/")
    suspend fun getGooglePlay(@Query("collection") collection: String, @Query("country") country: String, @Query("num") num: Int): Response<Apps>

    @GET("web/search/offers")
    suspend fun getEdadealMarket(@Query("locality") locality: String, @Query("retailer") retailer: List<String>, @Query("segment") segment: String? = null, @Query("sort") sort: String? = null, @Query("count") count: Int, @Query("page") page: Int): Response<ResponseBody>
}
