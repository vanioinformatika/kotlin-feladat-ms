package hu.vanio.kotlin.feladat.ms

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherAppTest {

    @Test fun `sikeres lekerdezes`() {
        val mockWeatherApp = Mockito.mock(WeatherApp::class.java)
        val daysWithTemperaturesMap = mutableMapOf<Int, Double>()
        daysWithTemperaturesMap[11] = 3.6
        daysWithTemperaturesMap[12] = 5.1
        Mockito.`when`(mockWeatherApp.calculateAvgTemp("https://goodURL.hu")).thenReturn(daysWithTemperaturesMap)
        val result = mockWeatherApp.calculateAvgTemp("https://goodURL.hu")
        verify(mockWeatherApp).calculateAvgTemp("https://goodURL.hu")
        assertEquals(result, daysWithTemperaturesMap)
    }

    @Test fun `rossz lekerdezes`() {
        val weatherApp = WeatherApp()
        val exception = assertThrows<DataUnavailableException> { weatherApp.calculateAvgTemp("https://api.open-meteo.com/v1/forecasto?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto") }
        assertTrue(exception.message!!.startsWith("Weather datas are unavailable! Response was:"))
    }

}