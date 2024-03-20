package hu.vanio.kotlin.feladat.ms.vaadin

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.server.ErrorEvent
import com.vaadin.flow.server.ErrorHandler
import org.springframework.stereotype.Component

@Component
class CustomErrorHandler : ErrorHandler {
    override fun error(errorEvent: ErrorEvent) {
        if (UI.getCurrent() != null) {
            UI.getCurrent().access {
                Notification.show("An internal error has occurred. (${errorEvent.throwable.message})")
            }
        }
    }
}