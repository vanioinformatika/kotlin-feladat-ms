package hu.vanio.kotlin.feladat.ms.vaadin

import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.SessionInitEvent
import com.vaadin.flow.server.VaadinServiceInitListener
import org.springframework.stereotype.Component

@Component
class VaadinInitListener(val customErrorHandler: CustomErrorHandler) : VaadinServiceInitListener {
    override fun serviceInit(serviceEvent: ServiceInitEvent) {
        serviceEvent.source.addSessionInitListener { initEvent: SessionInitEvent? -> initEvent?.session?.errorHandler = customErrorHandler}
    }
}