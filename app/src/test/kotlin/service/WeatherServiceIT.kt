package hu.vanio.kotlin.feladat.ms.service

import com.github.tomakehurst.wiremock.WireMockServer
import hu.vanio.kotlin.feladat.ms.mock.WeatherAPIMock.Companion.setupMockWeatherAPIResponse
import hu.vanio.kotlin.feladat.ms.parser.WeatherResponseParser
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ActiveProfiles("test")
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension::class)
@Import(
    WeatherService::class,
    WeatherResponseParser::class,
    TempCalculator::class
)
class WeatherServiceIT {
    companion object {
        private lateinit var wireMockServer: WireMockServer

        @BeforeAll
        @JvmStatic
        fun setUp() {
            wireMockServer = WireMockServer(1040)
            wireMockServer.start()
            setupMockWeatherAPIResponse(wireMockServer)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            wireMockServer.stop()
        }
    }

    @Autowired
    private lateinit var weatherService: WeatherService

    @Test
    fun `test get weekly temp data`() {
        runBlocking {
            val weeklyTempData = weatherService.getDailyTempData()
            assertNotNull(weeklyTempData)
        }
    }

    @Test
    fun `test get daily average temp for one week`() {
        runBlocking {
            val dailyAverageTempForOneWeek = weatherService.getDailyAverageTemp()
            assertNotNull(dailyAverageTempForOneWeek)
            assertEquals(LocalDate.parse("2024-03-20"), dailyAverageTempForOneWeek[0].date)
            assertEquals(7.137499999999999, dailyAverageTempForOneWeek[0].average_temp)

            assertEquals(LocalDate.parse("2024-03-21"), dailyAverageTempForOneWeek[1].date)
            assertEquals(9.845833333333333, dailyAverageTempForOneWeek[1].average_temp)

            assertEquals(LocalDate.parse("2024-03-22"), dailyAverageTempForOneWeek[2].date)
            assertEquals(12.1625, dailyAverageTempForOneWeek[2].average_temp)

            assertEquals(LocalDate.parse("2024-03-23"), dailyAverageTempForOneWeek[3].date)
            assertEquals(11.65, dailyAverageTempForOneWeek[3].average_temp)

            assertEquals(LocalDate.parse("2024-03-24"), dailyAverageTempForOneWeek[4].date)
            assertEquals(8.345833333333335, dailyAverageTempForOneWeek[4].average_temp)

            assertEquals(LocalDate.parse("2024-03-25"), dailyAverageTempForOneWeek[5].date)
            assertEquals(8.700000000000001, dailyAverageTempForOneWeek[5].average_temp)

            assertEquals(LocalDate.parse("2024-03-26"), dailyAverageTempForOneWeek[6].date)
            assertEquals(9.183333333333334, dailyAverageTempForOneWeek[6].average_temp)
        }
    }
}