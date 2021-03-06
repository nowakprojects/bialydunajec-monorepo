package org.bialydunajec.registrations.domain.campedition

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.AggregateId
import javax.persistence.Embeddable

@Embeddable
class CampRegistrationsEditionId(campEditionNumber: String) : AggregateId(campEditionNumber){

    constructor(campEditionNumber: Int) : this(campEditionNumber.toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
