package hu.vanio.kotlin.feladat.ms

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.math.abs

class WeatherAppTest {
    fun nearlyEqual(a : Map<String, Double>, b : Map<String, Double>) : Boolean
    {
        if (a.size != b.size)
            return false
        a.forEach { entry ->
            val bval = b.get(entry.key)
            if (bval == null || abs(entry.value - bval) > 0.01)
                return false
        }
        return true
    }

    fun getResult(json : String) : Map<String, Double> {
        val meteoData = getMeteoDataFromJson(json)
        val dayTemps = WeatherCalculate.recordData(meteoData)
        return WeatherCalculate.getDayAverages(dayTemps)
    }
    @Test fun empty() {
        val jsonString = """
        {
        	"latitude":47.5,
        	"longitude":19.0625,
        	"generationtime_ms":0.025987625122070312,
        	"utc_offset_seconds":3600,
        	"timezone":"Europe/Budapest",
        	"timezone_abbreviation":"CET",
        	"elevation":124.0,
        	"hourly_units":	
        	{
        		"time":"iso8601",
        		"temperature_2m":"°C"
        	},
        	"hourly":
        	{
        		"time":[
        		],
        		"temperature_2m":[
        		]
        	}
        }
        """
        val res = getResult(jsonString)
        assertTrue(res.isEmpty())
    }
    @Test fun oneData() {
        val jsonString = """
        {
        	"latitude":47.5,
        	"longitude":19.0625,
        	"generationtime_ms":0.025987625122070312,
        	"utc_offset_seconds":3600,
        	"timezone":"Europe/Budapest",
        	"timezone_abbreviation":"CET",
        	"elevation":124.0,
        	"hourly_units":	
        	{
        		"time":"iso8601",
        		"temperature_2m":"°C"
        	},
        	"hourly":
        	{
        		"time":[
                    "2024-03-20T00:00"
        		],
        		"temperature_2m":[
                    3.4
        		]
        	}
        }
        """
        val res = getResult(jsonString)
        val expectedRes = mapOf(
                "2024-03-20" to 3.4,
        )
        assertTrue ( expectedRes == res )
    }
    @Test fun multipleOneDay() {
        val jsonString = """
        {
        	"latitude":47.5,
        	"longitude":19.0625,
        	"generationtime_ms":0.025987625122070312,
        	"utc_offset_seconds":3600,
        	"timezone":"Europe/Budapest",
        	"timezone_abbreviation":"CET",
        	"elevation":124.0,
        	"hourly_units":	
        	{
        		"time":"iso8601",
        		"temperature_2m":"°C"
        	},
        	"hourly":
        	{
        		"time":[
                    "2024-03-20T00:00",
                    "2024-03-20T01:00",
                    "2024-03-20T02:00"
        		],
        		"temperature_2m":[
                    1.0,
                    2.0,
                    3.0
        		]
        	}
        }
        """
        val res = getResult(jsonString)
        val expectedRes = mapOf(
                "2024-03-20" to 2.0,
        )
        assertTrue ( expectedRes == res )
    }
    @Test fun ordered() {
        val jsonString = """
        {
        	"latitude":47.5,
        	"longitude":19.0625,
        	"generationtime_ms":0.025987625122070312,
        	"utc_offset_seconds":3600,
        	"timezone":"Europe/Budapest",
        	"timezone_abbreviation":"CET",
        	"elevation":124.0,
        	"hourly_units":	
        	{
        		"time":"iso8601",
        		"temperature_2m":"°C"
        	},
        	"hourly":
        	{
        		"time":[
                    "2024-03-20T00:00",
                    "2024-03-20T01:00",
                    "2024-03-20T02:00",
                    "2024-03-21T00:00",
                    "2024-03-21T01:00"
                ],
        		"temperature_2m":[
                    1.0,
                    2.0,
                    3.0,
                    2.0,
                    3.0
        		]
        	}
        }
        """
        val res = getResult(jsonString)
        val expectedRes = mapOf(
                "2024-03-20" to 2.0,
                "2024-03-21" to 2.5,
        )
        assertTrue ( expectedRes == res )
    }

    @Test fun nonOrdered() {
        val jsonString = """
        {
        	"latitude":47.5,
        	"longitude":19.0625,
        	"generationtime_ms":0.025987625122070312,
        	"utc_offset_seconds":3600,
        	"timezone":"Europe/Budapest",
        	"timezone_abbreviation":"CET",
        	"elevation":124.0,
        	"hourly_units":	
        	{
        		"time":"iso8601",
        		"temperature_2m":"°C"
        	},
        	"hourly":
        	{
        		"time":[
                    "2024-03-20T00:00",
                    "2024-03-20T01:00",
                    "2024-03-21T00:00",
                    "2024-03-21T01:00",
                    "2024-03-20T02:00"
                ],
        		"temperature_2m":[
                    1.0,
                    2.0,
                    2.0,
                    3.0,
                    3.0
        		]
        	}
        }
        """
        val res = getResult(jsonString)
        val expectedRes = mapOf(
                "2024-03-20" to 2.0,
                "2024-03-21" to 2.5,
        )
        assertTrue ( expectedRes == res )
    }
    @Test fun realData() {
        val jsonString = """
        {
            "latitude":47.5,
            "longitude":19.0625,
            "generationtime_ms":0.025987625122070312,
            "utc_offset_seconds":3600,
            "timezone":"Europe/Budapest",
            "timezone_abbreviation":"CET",
            "elevation":124.0,
            "hourly_units":	
            {
                "time":"iso8601",
                "temperature_2m":"°C"
            },
            "hourly":
            {
                "time":[
                    "2024-03-20T00:00",
                    "2024-03-20T01:00",
                    "2024-03-20T02:00",
                    "2024-03-20T03:00",
                    "2024-03-20T04:00",
                    "2024-03-20T05:00",
                    "2024-03-20T06:00",
                    "2024-03-20T07:00",
                    "2024-03-20T08:00",
                    "2024-03-20T09:00",
                    "2024-03-20T10:00",
                    "2024-03-20T11:00",
                    "2024-03-20T12:00",
                    "2024-03-20T13:00",
                    "2024-03-20T14:00",
                    "2024-03-20T15:00",
                    "2024-03-20T16:00",
                    "2024-03-20T17:00",
                    "2024-03-20T18:00",
                    "2024-03-20T19:00",
                    "2024-03-20T20:00",
                    "2024-03-20T21:00",
                    "2024-03-20T22:00",
                    "2024-03-20T23:00",
                    "2024-03-21T00:00",
                    "2024-03-21T01:00",
                    "2024-03-21T02:00",
                    "2024-03-21T03:00",
                    "2024-03-21T04:00",
                    "2024-03-21T05:00",
                    "2024-03-21T06:00",
                    "2024-03-21T07:00",
                    "2024-03-21T08:00",
                    "2024-03-21T09:00",
                    "2024-03-21T10:00",
                    "2024-03-21T11:00",
                    "2024-03-21T12:00",
                    "2024-03-21T13:00",
                    "2024-03-21T14:00",
                    "2024-03-21T15:00",
                    "2024-03-21T16:00",
                    "2024-03-21T17:00",
                    "2024-03-21T18:00",
                    "2024-03-21T19:00",
                    "2024-03-21T20:00",
                    "2024-03-21T21:00",
                    "2024-03-21T22:00",
                    "2024-03-21T23:00",
                    "2024-03-22T00:00",
                    "2024-03-22T01:00",
                    "2024-03-22T02:00",
                    "2024-03-22T03:00",
                    "2024-03-22T04:00",
                    "2024-03-22T05:00",
                    "2024-03-22T06:00",
                    "2024-03-22T07:00",
                    "2024-03-22T08:00",
                    "2024-03-22T09:00",
                    "2024-03-22T10:00",
                    "2024-03-22T11:00",
                    "2024-03-22T12:00",
                    "2024-03-22T13:00",
                    "2024-03-22T14:00",
                    "2024-03-22T15:00",
                    "2024-03-22T16:00",
                    "2024-03-22T17:00",
                    "2024-03-22T18:00",
                    "2024-03-22T19:00",
                    "2024-03-22T20:00",
                    "2024-03-22T21:00",
                    "2024-03-22T22:00",
                    "2024-03-22T23:00",
                    "2024-03-23T00:00",
                    "2024-03-23T01:00",
                    "2024-03-23T02:00",
                    "2024-03-23T03:00",
                    "2024-03-23T04:00",
                    "2024-03-23T05:00",
                    "2024-03-23T06:00",
                    "2024-03-23T07:00",
                    "2024-03-23T08:00",
                    "2024-03-23T09:00",
                    "2024-03-23T10:00",
                    "2024-03-23T11:00",
                    "2024-03-23T12:00",
                    "2024-03-23T13:00",
                    "2024-03-23T14:00",
                    "2024-03-23T15:00",
                    "2024-03-23T16:00",
                    "2024-03-23T17:00",
                    "2024-03-23T18:00",
                    "2024-03-23T19:00",
                    "2024-03-23T20:00",
                    "2024-03-23T21:00",
                    "2024-03-23T22:00",
                    "2024-03-23T23:00",
                    "2024-03-24T00:00",
                    "2024-03-24T01:00",
                    "2024-03-24T02:00",
                    "2024-03-24T03:00",
                    "2024-03-24T04:00",
                    "2024-03-24T05:00",
                    "2024-03-24T06:00",
                    "2024-03-24T07:00",
                    "2024-03-24T08:00",
                    "2024-03-24T09:00",
                    "2024-03-24T10:00",
                    "2024-03-24T11:00",
                    "2024-03-24T12:00",
                    "2024-03-24T13:00",
                    "2024-03-24T14:00",
                    "2024-03-24T15:00",
                    "2024-03-24T16:00",
                    "2024-03-24T17:00",
                    "2024-03-24T18:00",
                    "2024-03-24T19:00",
                    "2024-03-24T20:00",
                    "2024-03-24T21:00",
                    "2024-03-24T22:00",
                    "2024-03-24T23:00",
                    "2024-03-25T00:00",
                    "2024-03-25T01:00",
                    "2024-03-25T02:00",
                    "2024-03-25T03:00",
                    "2024-03-25T04:00",
                    "2024-03-25T05:00",
                    "2024-03-25T06:00",
                    "2024-03-25T07:00",
                    "2024-03-25T08:00",
                    "2024-03-25T09:00",
                    "2024-03-25T10:00",
                    "2024-03-25T11:00",
                    "2024-03-25T12:00",
                    "2024-03-25T13:00",
                    "2024-03-25T14:00",
                    "2024-03-25T15:00",
                    "2024-03-25T16:00",
                    "2024-03-25T17:00",
                    "2024-03-25T18:00",
                    "2024-03-25T19:00",
                    "2024-03-25T20:00",
                    "2024-03-25T21:00",
                    "2024-03-25T22:00",
                    "2024-03-25T23:00",
                    "2024-03-26T00:00",
                    "2024-03-26T01:00",
                    "2024-03-26T02:00",
                    "2024-03-26T03:00",
                    "2024-03-26T04:00",
                    "2024-03-26T05:00",
                    "2024-03-26T06:00",
                    "2024-03-26T07:00",
                    "2024-03-26T08:00",
                    "2024-03-26T09:00",
                    "2024-03-26T10:00",
                    "2024-03-26T11:00",
                    "2024-03-26T12:00",
                    "2024-03-26T13:00",
                    "2024-03-26T14:00",
                    "2024-03-26T15:00",
                    "2024-03-26T16:00",
                    "2024-03-26T17:00",
                    "2024-03-26T18:00",
                    "2024-03-26T19:00",
                    "2024-03-26T20:00",
                    "2024-03-26T21:00",
                    "2024-03-26T22:00",
                    "2024-03-26T23:00"
                ],
                "temperature_2m":[3.4,3.0,2.6,2.2,1.6,1.4,1.2,1.7,3.4,5.6,9.1,10.9,12.0,12.7,13.1,13.3,13.1,12.6,11.2,9.4,8.0,6.9,5.7,4.9,4.5,4.4,4.3,4.0,3.6,3.3,3.0,3.4,5.8,9.4,11.7,13.4,14.4,15.3,15.9,15.7,15.5,14.8,13.6,12.5,11.3,10.4,9.8,9.4,9.1,9.1,9.0,8.6,8.4,8.7,9.0,9.5,10.5,11.7,12.9,14.6,15.8,16.3,16.4,16.2,15.7,14.9,13.6,12.4,11.5,10.7,9.8,9.1,8.6,8.3,8.0,7.3,7.1,6.8,6.3,6.6,8.4,11.0,13.4,15.6,17.1,17.8,18.1,17.9,17.4,16.8,16.0,15.1,14.1,12.9,11.9,11.1,10.3,9.6,8.9,8.2,7.6,7.1,6.6,6.6,7.3,8.4,9.3,9.8,10.2,10.5,10.9,11.2,11.2,10.6,9.6,8.8,8.1,7.5,6.9,6.5,6.1,5.7,5.3,4.8,4.5,4.3,4.2,4.1,5.2,7.7,9.8,11.3,12.4,13.1,13.5,13.5,13.3,12.6,11.6,10.6,9.7,8.8,8.0,7.2,6.6,6.0,5.6,5.3,5.1,5.1,5.2,5.6,6.3,7.4,8.5,9.8,11.2,12.3,13.0,13.4,13.4,12.6,11.3,10.2,9.8,9.6,9.5,9.4]
            }
        }
        """
        val res = getResult(jsonString)
        val expectedRes = mapOf(
                "2024-03-20" to 7.041666666666665,
                "2024-03-21" to 9.558333333333335,
                "2024-03-22" to 11.812500000000002,
                "2024-03-23" to 12.233333333333334,
                "2024-03-24" to 8.820833333333333,
                "2024-03-25" to 8.6375,
                "2024-03-26" to 8.841666666666667
        )
        assertTrue ( expectedRes == res )
     }
    @Test fun badUrlEmptyData() {
        val meteoData = getMeteoDataFromUrl("badURL")
        assertTrue ( meteoData.hourly.time.size == 0 )
        assertTrue ( meteoData.hourly.temperature_2m.size == 0 )
    }
    @Test fun emptyJson() {
        val jsonString = """
        {
        }
        """
        val res = getResult(jsonString)
        assertTrue ( res.isEmpty() )
    }
    @Test fun badFormedJson() {
        val jsonString = """
        }
        """
        val res = getResult(jsonString)
        assertTrue ( res.isEmpty() )
    }
}