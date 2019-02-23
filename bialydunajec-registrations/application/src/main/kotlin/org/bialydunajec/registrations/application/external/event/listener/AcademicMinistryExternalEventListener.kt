package org.bialydunajec.registrations.application.external.event.listener

import org.bialydunajec.academicministry.messages.event.AcademicMinistryExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.SpringExternalEventListener
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.registrations.application.external.event.processor.AcademicMinistryExternalEventProcessor
import org.springframework.stereotype.Component

@Component
internal class AcademicMinistryExternalEventListener(
        private val academicMinistryExternalEventProcessor: AcademicMinistryExternalEventProcessor,
        externalEventSubscriber: ExternalEventSubscriber
): ExternalEventListener {

    init{
        externalEventSubscriber.subscribe<AcademicMinistryExternalEvent.AcademicMinistryCreated> {
            academicMinistryExternalEventProcessor.process(it.payload)
        }

        externalEventSubscriber.subscribe<AcademicMinistryExternalEvent.AcademicMinistryUpdated> {
            academicMinistryExternalEventProcessor.process(it.payload)
        }
    }
}
