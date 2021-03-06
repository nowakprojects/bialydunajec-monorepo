package org.bialydunajec.campedition.domain.campedition

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class CampEditionId(campEditionNumber: Int) : AggregateId(campEditionNumber.toString())
