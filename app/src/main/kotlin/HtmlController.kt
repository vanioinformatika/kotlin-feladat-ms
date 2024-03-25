package hu.vanio.kotlin.feladat.ms

import com.google.gson.Gson
import khttp.get
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.set

@Controller
class HtmlController {

    @Autowired
    lateinit var weatherAppService: WeatherAppService

    @GetMapping("/")
    fun index(model: Model): String {
        model["title"] = "Average Daily temperature list"
        val latitude = 47.4984
        val longitude = 19.0404
        val apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&hourly=temperature_2m&timezone=auto"
        try {
            val meteoData = weatherAppService.fetchMeteoData(apiUrl)
            model["averageTempList"] = weatherAppService.displayTemperatureDailyAverage(meteoData)
        } catch (exception: Exception) {
            println("Exception occured while fetching meteo data. Exception message: " + exception.message)
            model["averageTempList"] = arrayListOf("Exception occured while fetching meteo data.")
        }
        return "index"
    }



}
