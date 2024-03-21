package hu.vanio.kotlin.feladat.ms

import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.service.WeatherService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest
class WeatherAppTest {
    @Autowired
    lateinit var weatherService: WeatherService

    @Test
    fun testContext() {
        assertNotNull(weatherService)
    }
}