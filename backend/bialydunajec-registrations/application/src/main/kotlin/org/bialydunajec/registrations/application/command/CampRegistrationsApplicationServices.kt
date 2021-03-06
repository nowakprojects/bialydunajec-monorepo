package org.bialydunajec.registrations.application.command

import org.bialydunajec.ddd.application.base.ApplicationService
import org.bialydunajec.ddd.application.base.time.Clock
import org.bialydunajec.ddd.domain.sharedkernel.exception.DomainRuleViolationException
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEdition
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionRepository
import org.bialydunajec.registrations.domain.campedition.specification.CampRegistrationsCanStartSpecification
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantRepository
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.exception.CampRegistrationsDomainRule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation

//TODO: Add checking camp edition state if any other is in progress
internal class CreateCampRegistrationsEditionApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository
) : ApplicationService<CampRegistrationsCommand.CreateCampRegistrationsEdition> {

    override fun execute(command: CampRegistrationsCommand.CreateCampRegistrationsEdition) {
        val newCampEdition = CampRegistrationsEdition(
                campRegistrationsEditionId = command.campRegistrationsEditionId,
                editionStartDate = command.campEditionStartDate,
                editionEndDate = command.campEditionEndDate,
                totalPrice = command.totalPrice,
                downPaymentAmount = command.downPaymentAmount
        )
        campEditionRepository.save(newCampEdition)
    }
}

internal class UpdateCampRegistrationsEditionDurationApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration> {

    override fun execute(command: CampRegistrationsCommand.UpdateCampRegistrationsEditionDuration) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        campEdition.updateCampEditionDuration(command.campEditionStartDate, command.campEditionEndDate)
        campEditionRepository.save(campEdition)
    }
}

internal class SetupCampRegistrationsApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.UpdateCampRegistrationsTimer> {

    override fun execute(command: CampRegistrationsCommand.UpdateCampRegistrationsTimer) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        campEdition.updateCampRegistrationsTimer(command.timerSettings, clock.currentDateTime())
        campEditionRepository.save(campEdition)
    }
}


internal class StartCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.StartCampRegistrationsNow> {

    override fun execute(command: CampRegistrationsCommand.StartCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.startNowCampRegistrations(clock.currentDateTime(), CampRegistrationsCanStartSpecification(campEditionRepository))

        campEditionRepository.save(campEdition)
    }
}

internal class SuspendCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.SuspendCampRegistrationsNow> {

    override fun execute(command: CampRegistrationsCommand.SuspendCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.suspendNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

internal class UnsuspendCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.UnsuspendCampRegistrationsNow> {

    override fun execute(command: CampRegistrationsCommand.UnsuspendCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.unsuspendNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

internal class FinishCampRegistrationsNowApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val clock: Clock
) : ApplicationService<CampRegistrationsCommand.FinishCampRegistrationsNow> {

    override fun execute(command: CampRegistrationsCommand.FinishCampRegistrationsNow) {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        campEdition.finishNowCampRegistrations(clock.currentDateTime())

        campEditionRepository.save(campEdition)
    }
}

internal class CreateAcademicMinistryCottageApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        @Qualifier("cottageAcademicMinistryRepositoryImpl")
        private val academicMinistryRepository: AcademicMinistryRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateAcademicMinistryCottage> {

    override fun execute(command: CampRegistrationsCommand.CreateAcademicMinistryCottage): CottageId {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)
        val academicMinistry = academicMinistryRepository.findById(command.academicMinistryId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.ACADEMIC_MINISTRY_NOT_FOUND)

        val academicMinistryCottage = campEdition.createAcademicMinistryCottage(academicMinistry)

        return cottageRepository.save(academicMinistryCottage).getAggregateId()
    }
}

internal class CreateStandaloneCottageApplicationService(
        private val campEditionRepository: CampRegistrationsEditionRepository,
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.CreateStandaloneCottage> {

    override fun execute(command: CampRegistrationsCommand.CreateStandaloneCottage): CottageId {
        val campEdition = campEditionRepository.findById(command.campRegistrationsEditionId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.CAMP_EDITION_NOT_FOUND)

        val academicMinistryCottage = campEdition.createStandaloneCottage(command.cottageName)

        return cottageRepository.save(academicMinistryCottage).getAggregateId()
    }
}

internal class UpdateCottageApplicationService(
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.UpdateCottage> {

    override fun execute(command: CampRegistrationsCommand.UpdateCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)

        cottage.update(
                command.name,
                command.logoImageUrl,
                command.buildingPhotoUrl,
                command.place,
                command.cottageSpace,
                command.campersLimitations,
                command.bankTransferDetails,
                command.cottageBoss
        )

        cottageRepository.save(cottage)
    }
}

internal class ActivateCottageApplicationService(
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.ActivateCottage> {

    override fun execute(command: CampRegistrationsCommand.ActivateCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)
        cottage.activate()
        cottageRepository.save(cottage)
    }
}

internal class DeactivateCottageApplicationService(
        private val cottageRepository: CottageRepository
) : ApplicationService<CampRegistrationsCommand.DeactivateCottage> {

    override fun execute(command: CampRegistrationsCommand.DeactivateCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)
        cottage.deactivate()
        cottageRepository.save(cottage)
    }
}

internal class DeleteCottageApplicationService(
        private val cottageRepository: CottageRepository,
        private val campParticipantRepository: CampParticipantRepository
) : ApplicationService<CampRegistrationsCommand.DeleteCottage> {

    override fun execute(command: CampRegistrationsCommand.DeleteCottage) {
        val cottage = cottageRepository.findById(command.cottageId)
                ?: throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_NOT_FOUND)

        campParticipantRepository.countByCottageId(cottage.getAggregateId())
                .takeIf { it > 0 }
                ?.run { throw DomainRuleViolationException.of(CampRegistrationsDomainRule.COTTAGE_WITH_CAMP_PARTICIPANTS_CANNOT_BE_DELETED) }

        cottage.delete()
        cottageRepository.delete(cottage)
    }
}
