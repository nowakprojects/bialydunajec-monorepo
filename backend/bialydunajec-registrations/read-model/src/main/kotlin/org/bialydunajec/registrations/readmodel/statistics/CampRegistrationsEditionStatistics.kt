package org.bialydunajec.registrations.readmodel.statistics

import org.bialydunajec.ddd.dto.sharedkernel.GenderDto
import org.bialydunajec.registrations.dto.CamperApplicationWithCottageDto
import org.bialydunajec.registrations.dto.CottageSpaceDto
import org.bialydunajec.registrations.dto.ShirtTypeDto
import org.bialydunajec.registrations.messages.event.CampParticipantExternalEvent
import org.bialydunajec.registrations.messages.event.CottageExternalEvent
import org.bialydunajec.registrations.messages.event.ShirtOrderExternalEvent
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
internal class CampRegistrationsEditionStatistics(
    @Id
    val campRegistrationsEditionId: String,
    var cottagesStats: MutableList<CottageStats> = mutableListOf()
) {

    var registeredCampParticipants: Int = 0

    internal class CottageStats(
        val cottageId: String,
        var cottageName: String,
        var academicMinistryId: String?,
        var cottageSpace: CottageSpaceDto?,
        var maleCampParticipantsAmount: Int = 0,
        var femaleCampParticipantsAmount: Int = 0,
        var highSchoolRecentGraduatesAmount: Int = 0,
        val shirtOrdersStats: CottageShirtOrdersStats = CottageShirtOrdersStats()
    ) {
        fun getCampParticipantsAmount() = maleCampParticipantsAmount + femaleCampParticipantsAmount
        fun getCottageFillRatio() = getCampParticipantsAmount().div(cottageSpace?.fullCapacity?.toDouble() ?: 0.0)
    }

    fun calculateWith(eventPayload: CottageExternalEvent.CottageCreated) {
        with(eventPayload.snapshot) {
            cottagesStats.add(CottageStats(cottageId, name, academicMinistryId, cottageSpace))
        }
    }

    fun calculateWith(eventPayload: CottageExternalEvent.CottageDeleted) {
        with(eventPayload.snapshot) {
            cottagesStats.removeIf { it.cottageId == cottageId }
        }
    }

    fun removeCottageStats(cottageId: String) {
        cottagesStats.removeIf { it.cottageId == cottageId }
    }

    fun calculateWith(eventPayload: CottageExternalEvent.CottageUpdated) {
        eventPayload.snapshot.let { cottageSnapshot ->
            val cottageStats = cottagesStats.find { it.cottageId == cottageSnapshot.cottageId }
            cottageStats?.apply {
                cottageName = cottageSnapshot.name
                academicMinistryId = cottageSnapshot.academicMinistryId
                cottageSpace = cottageSnapshot.cottageSpace
            }
        }
    }

    fun calculateWith(eventPayload: CampParticipantExternalEvent.CampParticipantRegistered) {
        increaseStatsBasedOn(eventPayload.snapshot.currentCamperData)
    }

    fun calculateWith(eventPayload: CampParticipantExternalEvent.CampParticipantDataCorrected) {
        decreaseStatsBasedOn(eventPayload.oldCamperData)
        increaseStatsBasedOn(eventPayload.newCamperData)
    }

    fun calculateWith(eventPayload: CampParticipantExternalEvent.CampParticipantUnregisteredByAuthorized) {
        decreaseStatsBasedOn(eventPayload.snapshot.currentCamperData)
    }

    private fun increaseStatsBasedOn(camperData: CamperApplicationWithCottageDto) {
        registeredCampParticipants += 1
        with(camperData) {
            cottagesStats.find { it.cottageId == cottage.cottageId }
                ?.apply {
                    when (personalData.gender) {
                        GenderDto.FEMALE -> femaleCampParticipantsAmount++
                        GenderDto.MALE -> maleCampParticipantsAmount++
                    }
                    if (camperEducation.isHighSchoolRecentGraduate) {
                        highSchoolRecentGraduatesAmount++
                    }
                }
        }
    }

    private fun decreaseStatsBasedOn(camperData: CamperApplicationWithCottageDto) {
        registeredCampParticipants -= 1
        cottagesStats.find { it.cottageId == camperData.cottage.cottageId }
            ?.apply {
                when (camperData.personalData.gender) {
                    GenderDto.FEMALE -> femaleCampParticipantsAmount--
                    GenderDto.MALE -> maleCampParticipantsAmount--
                }
                if (camperData.camperEducation.isHighSchoolRecentGraduate) {
                    highSchoolRecentGraduatesAmount--
                }
            }
    }

    fun calculateWith(eventPayload: ShirtOrderExternalEvent.OrderPlaced) {
        val cottageStats = cottagesStats.find { it.cottageId == eventPayload.campParticipant.cottage.cottageId }
        cottageStats?.shirtOrdersStats?.calculateWith(eventPayload)
    }

    fun calculateWith(eventPayload: ShirtOrderExternalEvent.OrderCancelled) {
        val cottageStats = cottagesStats.find { it.cottageId == eventPayload.campParticipant.cottage.cottageId }
        cottageStats?.shirtOrdersStats?.calculateWith(eventPayload)
    }

    fun getCampCapacity() = cottagesStats.map { it.cottageSpace?.fullCapacity ?: 0 }.sum()
    fun getCampFillRatio() = registeredCampParticipants.toDouble().div(cottagesStats.map {
        it.cottageSpace?.fullCapacity ?: 0
    }.sum().toDouble())

}

internal class CottageShirtOrdersStats(
    val shirtColorOptionStats: MutableList<ShirtColorOptionStats> = mutableListOf<ShirtColorOptionStats>(),
    val shirtSizeOptionStats: MutableList<ShirtSizeOptionStats> = mutableListOf<ShirtSizeOptionStats>()
) {
    fun calculateWith(eventPayload: ShirtOrderExternalEvent.OrderPlaced) {
        val shirtColorOptionStats: ShirtColorOptionStats =
            shirtColorOptionStats.find { it.shirtColorOptionId == eventPayload.shirtOrder.shirtColorOptionId }
                ?: ShirtColorOptionStats(
                    eventPayload.shirtOrder.shirtColorOptionId,
                    eventPayload.shirtOrder.colorName,
                    0
                ).apply { shirtColorOptionStats.add(this) }
        shirtColorOptionStats.ordersAmount++

        val shirtSizeOptionStats: ShirtSizeOptionStats =
            shirtSizeOptionStats.find { it.shirtSizeOptionId == eventPayload.shirtOrder.shirtSizeOptionId }
                ?: ShirtSizeOptionStats(
                    eventPayload.shirtOrder.shirtSizeOptionId,
                    eventPayload.shirtOrder.sizeName,
                    eventPayload.shirtOrder.shirtType,
                    0
                ).apply { shirtSizeOptionStats.add(this) }
        shirtSizeOptionStats.ordersAmount++
    }

    fun calculateWith(eventPayload: ShirtOrderExternalEvent.OrderCancelled) {
        shirtColorOptionStats.find { it.shirtColorOptionId == eventPayload.shirtOrder.shirtColorOptionId }
            ?.let { it.ordersAmount-- }

        shirtSizeOptionStats.find { it.shirtSizeOptionId == eventPayload.shirtOrder.shirtSizeOptionId }
            ?.let { it.ordersAmount-- }
    }
}

internal class ShirtColorOptionStats(
    val shirtColorOptionId: String,
    var colorName: String,
    var ordersAmount: Int = 0
)

internal class ShirtSizeOptionStats(
    val shirtSizeOptionId: String,
    var sizeName: String,
    var shirtType: ShirtTypeDto,
    var ordersAmount: Int = 0
)
