package org.bialydunajec.registrations.messages.event

import org.bialydunajec.registrations.dto.CampParticipantDto


sealed class CampParticipantExternalEvent {

    data class CampParticipantRegistered(
        val campParticipantId: String,
        val campParticipantRegistrationId: String?,
        val snapshot: CampParticipantDto
    ) : CampParticipantExternalEvent()

    data class CampParticipantConfirmed(
            val campParticipantId: String,
            val snapshot: CampParticipantDto
    ) : CampParticipantExternalEvent()
}