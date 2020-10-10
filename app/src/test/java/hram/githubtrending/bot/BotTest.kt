package hram.githubtrending.bot

import com.github.kotlintelegrambot.bot
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.from
import me.liuwj.ktorm.dsl.select
import me.liuwj.ktorm.dsl.where
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.forEach
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.logging.ConsoleLogger
import me.liuwj.ktorm.logging.LogLevel
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.varchar
import org.jsoup.Jsoup
import org.junit.Test

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
                val trendingLang = kotlin.runCatching { body.select(trendingModel.language).text() }
                    .getOrNull() ?: kotlin.runCatching { body.select(trendingModel.languageFallback).text() }.getOrNull()
                val todayStars = kotlin.runCatching { body.select(trendingModel.todayStars).text() }
                    .getOrNull() ?: kotlin.runCatching { body.select(trendingModel.todayStarsFallback).text() }.getOrNull()
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
                val result = bot.sendMessage(groupIdGithubTrends, "https://github.com/${split!![0].trim()}/${split[1].trim()}\nStars: ${item.stars}\nForks: ${item.stars}\n${item.todayStars}")
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

    @Test
    fun googlePlay() = runBlocking {
        val map = HashSet<String>()
        database
            .from(Resources)
            .select(Resources.name)
            .where { Resources.type eq "topselling_free_ru" }
            .forEach { line -> map.add(line.getString(1)!!) }

        val response = JsoupProvider.getTrendingService("http://localhost:3000/").getGooglePlay("topselling_free", "ru", 100)
        response.body()!!.results.forEach { item ->
            if (!map.contains(item.appId)) {
                val result = bot.sendMessage(groupIdGooglePlayTrends, "${item.playstoreUrl}\n\n\n${item.title}\nScore: ${item.scoreText}")
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
}

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