package org.bialydunajec.registrations.domain.camperapplication

import org.bialydunajec.ddd.domain.aggregate.AggregateRoot
import org.bialydunajec.registrations.domain.cottage.CottageId
import javax.persistence.*

@Entity
class CamperApplication internal constructor(
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "aggregateId", column = Column(name = "cottageId")))
        val cottageId: CottageId
) : AggregateRoot<CamperApplicationId, CamperApplicationEvents>(CamperApplicationId()) {
}