package hu.vanio.kotlin.feladat.ms.httpclient

import feign.RequestLine
import hu.vanio.kotlin.feladat.ms.data.WeatherResponse
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "weatherFeignClient")
interface WeatherFeignClient {
    @RequestLine("GET /forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto")
    fun  getWeather(): WeatherResponse
}