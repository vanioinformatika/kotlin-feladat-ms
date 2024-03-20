
import hu.vanio.kotlin.feladat.ms.WeatherCommandLineRunner
import hu.vanio.kotlin.feladat.ms.exception.ServiceUnavailable
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherDailyForecast
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherForecasts
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.LocalDate
import java.time.Month

class WeatherCommandLineRunnerTest {
    @MockK
    lateinit var weatherService: WeatherService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testConsoleLog() {
        every { weatherService.getWeatherStatistics() } returns WeatherForecasts(listOf(
                WeatherDailyForecast(LocalDate.of(2024, Month.MARCH, 1), listOf(1.0, 2.0, 3.0))))
        val weatherCommandLineRunner = WeatherCommandLineRunner(weatherService)

        assertStandardOutput("----------------------------------------------\n" +
                "Weather forecast daily averages:\n" +
                "----------------------------------------------\n" +
                "2024-03-01 | 2.0\n" +
                "----------------------------------------------") { weatherCommandLineRunner.run() }
    }

    @Test
    fun testServiceUnavailable() {
        every { weatherService.getWeatherStatistics() } throws ServiceUnavailable("")
        val weatherCommandLineRunner = WeatherCommandLineRunner(weatherService)

        assertStandardOutput("Weather forecast service is unavailable") { weatherCommandLineRunner.run() }
    }

    private fun assertStandardOutput(expectedStdOut: String, functionCall: Runnable) {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val originalOut = System.out

        try {
            System.setOut(printStream)

            functionCall.run()

            assertEquals(expectedStdOut, outputStream.toString().trim())
        } finally {
            System.setOut(originalOut)
        }
    }
}