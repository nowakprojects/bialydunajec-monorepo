package org.bialydunajec.registrations.domain.camper.campparticipant

import org.bialydunajec.ddd.domain.base.event.DomainEvent
import org.bialydunajec.registrations.domain.camper.valueobject.CampParticipantSnapshot

sealed class CampParticipantEvent : DomainEvent<CampParticipantId> {
    data class Registered(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()

    data class Confirmed(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()

    data class Updated(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()

    data class UnregisteredByAuthorized(
            override val aggregateId: CampParticipantId,
            val snapshot: CampParticipantSnapshot
    ) : CampParticipantEvent()
}