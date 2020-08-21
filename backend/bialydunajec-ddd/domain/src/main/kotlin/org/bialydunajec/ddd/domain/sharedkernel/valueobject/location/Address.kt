package org.bialydunajec.ddd.domain.sharedkernel.valueobject.location

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.validation.constraints.NotNull

@Embeddable
data class Address(
        @NotNull
        val street: Street? = null,

        @Embedded
        val homeNumber: HomeNumber? = null,

        @NotNull
        @Embedded
        val city: CityName? = null,

        @NotNull
        @Embedded
        val postalCode: PostalCode? = null
) : ValueObject