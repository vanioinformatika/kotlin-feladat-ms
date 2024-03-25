package hu.vanio.kotlin.feladat.ms.mock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.StreamUtils.copyToString
import java.io.IOException
import java.nio.charset.Charset

class WeatherAPIMock {
    companion object {
        @JvmStatic
        @Throws(IOException::class)
        fun setupMockWeatherAPIResponse(mockServer: WireMockServer) {
            mockServer.stubFor(
                WireMock.get(WireMock.urlPathMatching("/v1/forecast.*"))
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                            .withBody(
                                copyToString(
                                    WeatherAPIMock::class.java.classLoader.getResourceAsStream("payload/weather-forecast-response.json"),
                                    Charset.defaultCharset()
                                )
                            )
                    )
            )
        }
    }
}