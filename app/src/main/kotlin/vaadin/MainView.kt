package hu.vanio.kotlin.feladat.ms.vaadin

import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherDailyForecast
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherService

@PageTitle("Weather forecast")
@Route
class MainView(private val weatherService: WeatherService) : HorizontalLayout() {
    private val table = Grid<WeatherDailyForecast>()
    private val refresh = Button("Refresh").also {
        it.addClickListener { _ ->
            refreshTableContent()
            Notification.show("Table content refreshed") }
        it.addClickShortcut(Key.ENTER)
    }

    private fun refreshTableContent() {
        val weatherStatistics = weatherService.getWeatherStatistics()
        table.setItems(weatherStatistics.weatherDailyForecast)
    }

    init {
        table.addColumn(WeatherDailyForecast::day).setHeader("Day")
        table.addColumn(WeatherDailyForecast::average).setHeader("Daily average temperature")

        super.setMargin(true)
        super.setVerticalComponentAlignment(FlexComponent.Alignment.END, table, refresh)
        super.add(table, refresh)
    }
}