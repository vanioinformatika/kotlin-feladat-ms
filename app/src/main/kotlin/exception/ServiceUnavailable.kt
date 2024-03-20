package hu.vanio.kotlin.feladat.ms.exception

class ServiceUnavailable(private val serviceId: String): RuntimeException() {
    override val message: String
        get() = "Service $serviceId is unavailable"
}