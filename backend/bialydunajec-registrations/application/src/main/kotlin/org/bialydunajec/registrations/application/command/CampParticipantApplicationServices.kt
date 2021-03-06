package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.application.base.concurrency.ProcessingSerializedQueue
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantFactory
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistration
import org.bialydunajec.registrations.domain.camper.campparticipantregistration.CampParticipantRegistrationRepository
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountFactory
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.bialydunajec.registrations.domain.payment.CampParticipantCottageAccountRepository
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtRepository
import org.bialydunajec.registrations.domain.shirt.ShirtOrderRepository

internal class CampParticipantRegistrationApplicationService(
        private val campParticipantFactory: CampParticipantFactory,
        private val campParticipantPaymentFactory: CampParticipantCottageAccountFactory,
        private val campParticipantRepository: CampParticipantRepository,
        private val campEditionShirtRepository: CampEditionShirtRepository,
        private val shirtOrderRepository: ShirtOrderRepository,
        private val campParticipantRegistrationRepository: CampParticipantRegistrationRepository,
        private val campParticipantCottageAccountRepository: CampParticipantCottageAccountRepository
) : ApplicationService<CampRegistrationsCommand.RegisterCampParticipantCommand> {

    private val processingQueue = ProcessingSerializedQueue<CampRegistrationsCommand.RegisterCampParticipantCommand> { processCommand(it) }

    override fun execute(command: CampRegistrationsCommand.RegisterCampParticipantCommand) =
            processingQueue.process(command)

    fun processCommand(command: CampRegistrationsCommand.RegisterCampParticipantCommand){
        campParticipantFactory.createCampParticipant(command.campRegistrationsEditionId, command.camperApplication)
                .let { campParticipantRepository.save(it) }
                .also { campParticipant ->
                    val campEditionShirt = campEditionShirtRepository.findByCampRegistrationsEditionId(command.campRegistrationsEditionId)
                            ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.SHIRT_TO_ORDER_MUST_EXISTS)
                    val shirtOrder = campEditionShirt.placeOrder(campParticipant, command.shirtOrder.shirtColorOptionId, command.shirtOrder.shirtSizeOptionId)
                    shirtOrderRepository.save(shirtOrder)
                    campParticipantRegistrationRepository.save(CampParticipantRegistration.createFrom(campParticipant.getSnapshot(), shirtOrder.getSnapshot()))
                }
                .also {
                    val campParticipantCottageAccount = campParticipantPaymentFactory.createFor(it)
                    campParticipantCottageAccountRepository.save(campParticipantCottageAccount)
                }
                .getAggregateId()
    }

}

internal class UnregisterCampParticipantApplicationService(
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.UnregisterCampParticipantByAuthorizedCommand> {

    override fun execute(command: CampRegistrationsCommand.UnregisterCampParticipantByAuthorizedCommand) {
        val campParticipant = campParticipantRepository.findById(command.campParticipantId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_TO_UNREGISTER__MUST_EXISTS)

        campParticipant.unregisterByAuthorized()

        campParticipantRepository.delete(campParticipant)
    }

}

internal class CorrectCampParticipantRegistrationDataApplicationService(
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.CorrectCampParticipantRegistrationDataCommand> {

    override fun execute(command: CampRegistrationsCommand.CorrectCampParticipantRegistrationDataCommand) {
        val campParticipant = campParticipantRepository.findById(command.campParticipantId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_PARTICIPANT_MUST_EXISTS_TO_UPDATE_REGISTRATION_DATA)

        campParticipant.correctCampParticipantData(command.camperApplication)

        campParticipantRepository.save(campParticipant)
    }

}
