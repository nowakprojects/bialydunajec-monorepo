package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("emailAddressReadModel")
data class EmailAddress(
        @Id
        var emailAddressId: String,
        var emailAddress: String,
        var previousEmailAddressId: String? = null,
        var ownerFirstName: String? = null,
        var ownerLastName: String? = null,
        var isActive: Boolean? = null,
        var emailGroupName: String? = null
)