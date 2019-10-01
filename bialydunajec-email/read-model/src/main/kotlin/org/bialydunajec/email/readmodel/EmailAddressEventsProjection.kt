package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.springframework.stereotype.Component

const val DEFAULT_EMAIL_ADDRESS_GROUP: String = "DEFAULT_GROUP";

@Component
internal class EmailAddressEventsProjection(
        private val emailAddressRepository: EmailAddressMongoRepository,
        private val emailAddressStatisticsMongoRepository: EmailAddressStatisticsMongoRepository,
        private val emailAddressEventStream: EmailAddressEventStream,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCreated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressDeactivated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressUpdated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressBelongingToGroupUpdated::class) {
            processingQueue.process(it)
        }

    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailAddressExternalEvent.EmailAddressCreated -> {
                with(payload) {
                    emailAddressRepository.findById(emailId).orElseGet { EmailAddress(emailId, emailAddress) }
                            .also {
                                it.isActive = isActive
                            }.also {
                                emailAddressRepository.save(it)
                            }

                    emailAddressStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_STATISTICS_ID)
                            .ifPresent {
                                if (emailId.contains(DEFAULT_EMAIL_ADDRESS_GROUP)){
                                    it.addressesCount++
                                }
                                emailAddressStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup -> {
                with(payload) {
                    emailAddressRepository.findById(emailId).orElseGet { EmailAddress(emailId, emailAddress) }
                            .also {
                                it.previousEmailAddressId = previousEmailAddressId
                                it.isActive = true
                                it.emailGroupName = emailGroupName
                                it.ownerFirstName = ownerFirstName
                                it.ownerLastName = ownerLastName
                            }.also {
                                emailAddressRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressDeactivated -> {
                with(payload) {
                    emailAddressRepository.findById(emailId).orElseGet { EmailAddress(emailId, emailAddress) }
                            .also {
                                it.isActive = false
                            }.also {
                                emailAddressRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressUpdated -> {
                with(payload) {
                    emailAddressRepository.findById(newEmailId).orElseGet { EmailAddress(newEmailId, newEmailAddress) }
                            .also {
                                it.previousEmailAddressId = previousEmailAddressId
                                it.isActive = true
                            }.also {
                                emailAddressRepository.save(it)
                            }
                    emailAddressStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_STATISTICS_ID)
                            .ifPresent {
                                if (newEmailId.contains(DEFAULT_EMAIL_ADDRESS_GROUP)) it.addressesCount--
                                emailAddressStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressBelongingToGroupUpdated -> {
                with(payload) {
                    emailAddressRepository.findById(newEmailId).orElseGet { EmailAddress(newEmailId, newEmailAddress) }
                            .also {
                                it.previousEmailAddressId = previousEmailAddressId
                                it.isActive = true
                                it.emailGroupName = emailGroupName
                                it.ownerFirstName = ownerFirstName
                                it.ownerLastName = ownerLastName
                            }.also {
                                emailAddressRepository.save(it)
                            }
                    emailAddressStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_STATISTICS_ID)
                            .ifPresent {
                                if (newEmailId.contains(DEFAULT_EMAIL_ADDRESS_GROUP)) it.addressesCount--
                                emailAddressStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }
        }
    }

}