package hu.vanio.kotlin.feladat.ms

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
@Theme
class WeatherApp: AppShellConfigurator

fun main() {
    runApplication<WeatherApp>()
}

