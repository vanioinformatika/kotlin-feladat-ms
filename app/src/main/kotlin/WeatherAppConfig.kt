package hu.vanio.kotlin.feladat.ms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "weather")
class WeatherAppConfig(var appUrl: String = "")
