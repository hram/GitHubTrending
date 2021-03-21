package hram.githubtrending.bot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.forEach
import me.liuwj.ktorm.dsl.from
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.dsl.select
import me.liuwj.ktorm.dsl.where
import me.liuwj.ktorm.logging.ConsoleLogger
import me.liuwj.ktorm.logging.LogLevel
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.varchar
import org.jsoup.Jsoup
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.nio.ByteBuffer
import java.util.UUID
import kotlin.collections.HashSet

class BotTest {

    private val groupIdGithubTrends: Long = BuildConfig.GROUP_ID_GITHUB_TRENDS

    private val groupIdGooglePlayTrends: Long = BuildConfig.GROUP_ID_GOOGLE_PLAY_TRENDS

    private val bot = bot { token = BuildConfig.BOT_TOKEN }

    private val database = Database.connect(
        url = BuildConfig.DATABASE_URL,
        driver = "com.mysql.jdbc.Driver",
        user = BuildConfig.DATABASE_USER,
        password = BuildConfig.DATABASE_PASSWORD,
        logger = ConsoleLogger(LogLevel.INFO)
    )

    @Test
    fun update() = runBlocking {
        val map = HashSet<String>()
        database
            .from(Resources)
            .select(Resources.name)
            .where { Resources.type eq "kotlin_daily" }
            .forEach { line -> map.add(line.getString(1)!!) }

        val trendingModel = TrendingConfigModel()

        val document = Jsoup.parse(JsoupProvider.getTrendingService("https://github.com/trending/").getTrending("kotlin", "daily").body(), "")
        val list = document.select(trendingModel.listName)
        val trendingList = arrayListOf<TrendingModel>()
        list.select(trendingModel.listNameSublistTag)?.let { li ->
            trendingList.addAll(li.map { body ->
                val trendingLang = kotlin.runCatching { body.select(trendingModel.language).text() }.getOrNull() ?: kotlin.runCatching { body.select(trendingModel.languageFallback).text() }.getOrNull()
                val todayStars = kotlin.runCatching { body.select(trendingModel.todayStars).text() }.getOrNull() ?: kotlin.runCatching { body.select(trendingModel.todayStarsFallback).text() }.getOrNull()
                val title = kotlin.runCatching { body.select(trendingModel.title).text() }.getOrNull()
                val description = kotlin.runCatching { body.select(trendingModel.description).text() }.getOrNull()
                val stars = kotlin.runCatching { body.select(trendingModel.stars).text() }.getOrNull()
                val forks = kotlin.runCatching { body.select(trendingModel.forks).text() }.getOrNull()
                TrendingModel(title, description, trendingLang, stars, forks, todayStars)
            })
        }

        trendingList.forEach { item ->
            if (!map.contains(item.title)) {
                val split = item.title?.trim()?.split("/")
                val result = bot.sendMessage(ChatId.fromId(groupIdGithubTrends), "https://github.com/${split!![0].trim()}/${split[1].trim()}\nStars: ${item.stars}\nForks: ${item.stars}\n${item.todayStars}")
                if (result.first?.body()?.ok == true) {
                    database.insert(Resources) {
                        set(it.name, item.title)
                        set(it.type, "kotlin_daily")
                    }
                    delay(5000)
                } else {
                    return@runBlocking
                }
            }
        }
    }

    @Ignore
    @Test
    fun googlePlay() = runBlocking {
        val map = HashSet<String>()
        database
            .from(Resources)
            .select(Resources.name)
            .where { Resources.type eq "topselling_free_ru" }
            .forEach { line -> map.add(line.getString(1)!!) }

        val response = JsoupProvider.getTrendingService("http://localhost:3000/").getGooglePlay("topselling_free", "ru", 200)
        response.body()!!.results.forEach { item ->
            if (!map.contains(item.appId)) {
                val result = bot.sendMessage(ChatId.fromId(groupIdGooglePlayTrends), "${item.playstoreUrl}&hl=ru\n\n\n${item.title}\nScore: ${item.scoreText}")
                if (result.first?.body()?.ok == true) {
                    database.insert(Resources) {
                        set(it.name, item.appId)
                        set(it.type, "topselling_free_ru")
                    }
                    delay(5000)
                } else {
                    return@runBlocking
                }
            }
        }
    }

    @Ignore
    @Test
    fun perekrestok() = runBlocking {
        // tea чай
        // turkey индейка
        // sausage колбасы
        val segment = "turkey" // "ground-coffee" // кофе молотый
        val response = JsoupProvider.getTrendingService("https://api.edadeal.ru/").getEdadealMarket(
            locality = "sankt-peterburg",
            retailer = listOf("5ka", "perekrestok"),
            segment = segment,
            sort = "ddiscount",
            count = 30,
            page = 1
        )

        val obj = ProtoBuf.decodeFromByteArray<TestArray>(response.body()!!.bytes())
        obj.list.forEach { item ->
            println("${item.name}\n${item.oldPrice} -> ${item.newPrice}\n${item.discountFloat} ${item.discountMeasure}\nДо: ${item.dateTo}")
            println("ID товара: ${getGuidFromByteArray(item.id)}")
            //println("ID: ${getGuidFromByteArray(item.string3)}")
            //println("ID: ${getGuidFromByteArray(item.value4!!)}")
            //println("ID: ${getGuidFromByteArray(item.value5!!)}")
            println("ID магазина?: ${getGuidFromByteArray(item.retailerId!!)}")
            println()
        }

        //println(response.body()!!)
        //File("response_raw_$segment").writeBytes(response.body()!!.bytes())
        //File("response_base64").writeText(Base64.getEncoder().encodeToString(response.body()!!.bytes()))
    }

    private fun getGuidFromByteArray(bytes: ByteArray) = ByteBuffer.wrap(bytes).let { UUID(it.long, it.long).toString() }

    @Ignore
    @Test
    fun protobuf2() {
        val bytes = File("response_raw_ground-coffee").readBytes()
        val obj = ProtoBuf.decodeFromByteArray<TestArray>(bytes)
        obj.list.forEach { item ->
            println(item)
            println()
        }
    }
}

@Serializable
data class TestArray(val list: Array<TestItem>)

@Serializable
data class PriceForUnit(val float1: Float, val price: Float, val unitType: String, val unitValue: Int)

@Serializable
data class TestItem(
    val id: ByteArray,
    val name: String,
    val imageUrl: String,
    val oldPrice: Float,
    val newPrice: Float,
    val string2: String? = null,
    val string3: ByteArray,
    val measureValue: Float? = null,
    val measureUnit: String? = null,
    val discountFloat: Float? = null,
    val discountMeasure: String? = null,
    val value: String? = null,
    val discountInt: Int? = null,
    val value1: String? = null,
    val dateFrom: String,
    val dateTo: String,
    val value2: String? = null,
    val value3: String? = null,
    val value4: ByteArray? = null,
    val priceForUnit: PriceForUnit? = null,
    val value5: ByteArray? = null,
    val retailerId: ByteArray? = null,
    val value7: String? = null,
    val value8: Int? = null
)

object Resources : Table<Nothing>("resources") {
    val name = varchar("name")
    val type = varchar("type")
}

data class TrendingConfigModel(
    var description: String = ".Box-row > p",
    var forks: String = ".f6 > a[href*=/network]",
    var language: String = ".f6 span[itemprop=programmingLanguage]",
    var languageFallback: String = ".f6 span[itemprop=programmingLanguage]",
    var listName: String = ".Box",
    var listNameSublistTag: String = "article",
    var stars: String = ".f6 > a[href*=/stargazers]",
    var title: String = ".Box-row > h1 > a",
    var todayStars: String = ".f6 > span.float-sm-right",
    var todayStarsFallback: String = ".f6 > span.float-sm-right"
)

data class TrendingModel(
    val title: String? = null,
    val description: String? = null,
    val language: String? = null,
    val stars: String? = null,
    val forks: String? = null,
    val todayStars: String? = null
)
