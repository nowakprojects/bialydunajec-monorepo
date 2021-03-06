package org.bialydunajec.ddd.infrastructure.base.external.command

import org.bialydunajec.ddd.application.base.external.command.ExternalCommand
import org.bialydunajec.ddd.application.base.external.command.ExternalCommandBus
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class SpringExternalCommandBus
    : ExternalCommandBus, ApplicationEventPublisherAware {

    private val log = LoggerFactory.getLogger(this.javaClass)

    lateinit var commandPublisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.commandPublisher = applicationEventPublisher
    }

    override fun send(command: ExternalCommand<*>) {
        commandPublisher.publishEvent(command)
        log.debug("External command sent by SpringExternalCommandBus: $command")
    }

}
