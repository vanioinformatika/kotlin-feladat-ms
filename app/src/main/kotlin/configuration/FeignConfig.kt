package hu.vanio.kotlin.feladat.ms.configuration

import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.okhttp.OkHttpClient
import hu.vanio.kotlin.feladat.ms.httpclient.WeatherFeignClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {
    @Value("\${base.url.weather.api}")
    private lateinit var weatherApiBaseUrl: String
    @Bean
    fun weatherFeignClient(): WeatherFeignClient {
        return Feign.builder()
            .client(OkHttpClient())
            .encoder(JacksonEncoder())
            .decoder(JacksonDecoder())
            .target(WeatherFeignClient::class.java, weatherApiBaseUrl)
    }
}