package hu.vanio.kotlin.feladat.ms.it

import com.github.tomakehurst.wiremock.WireMockServer
import hu.vanio.kotlin.feladat.ms.httpclient.WeatherFeignClient
import hu.vanio.kotlin.feladat.ms.mock.WeatherAPIMock.Companion.setupMockWeatherAPIResponse
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertEquals

@SpringBootTest
@ActiveProfiles("test")
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension::class)
class WeatherFeignClientIT {
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
    private lateinit var weatherFeignClient: WeatherFeignClient

    @Test
    fun `test weather feign client get weather`() {
        val weatherResponse = weatherFeignClient.getWeather()

        assertEquals(47.5, weatherResponse.latitude)
        assertEquals(19.0625, weatherResponse.longitude)
        assertEquals(168, weatherResponse.hourly.time?.size)
        assertEquals(168, weatherResponse.hourly.temperature_2m?.size)
    }
}