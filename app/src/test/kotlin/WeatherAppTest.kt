package hu.vanio.kotlin.feladat.ms

import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import kotlin.test.Test
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatusCode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.HttpServerErrorException
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(controllers = [HtmlController::class])
class WeatherAppTest{

    @MockBean
    lateinit var weatherAppService: WeatherAppService

    @MockBean
    lateinit var meteoResponseData: MeteoResponseData

    @Autowired
    lateinit var mockMvc: MockMvc

    object MockitoHelper {
        fun <T> anyObject(): T {
            Mockito.any<T>()
            return uninitialized()
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> uninitialized(): T =  null as T
    }


    @Test fun `sikeres lekerdezes`() {
        mockMvc.perform(get("/")).andExpect(status().isOk)
            .andExpect(content()
                .string("<html>\n" +
                        "<head>\n" +
                        "    <title>Average Daily temperature list</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<h1>Average Daily temperature list</h1>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>"))
    }

    @Test fun `sikertelen lekerdezes`() {
        `when`(weatherAppService.fetchMeteoData(MockitoHelper.anyObject())).thenThrow(HttpServerErrorException(
            HttpStatusCode.valueOf(500)))
        mockMvc.perform(get("/")).andExpect(status().isOk)
            .andExpect(content()
                .string("<html>\n" +
                        "<head>\n" +
                        "    <title>Average Daily temperature list</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<h1>Average Daily temperature list</h1>\n" +
                        "\n" +
                        "<h3>Exception occured while fetching meteo data.</h3>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>"))
    }
}