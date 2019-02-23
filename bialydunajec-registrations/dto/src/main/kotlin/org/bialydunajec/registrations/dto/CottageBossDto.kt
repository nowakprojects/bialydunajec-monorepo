package org.bialydunajec.registrations.dto

import org.bialydunajec.ddd.base.dto.ExtendedDescriptionDto


class CottageBossDto(
        val firstName: String?,

        val lastName: String?,

        val phoneNumber: String?,

        val emailAddress: String?,

        val university: String?,

        val fieldOfStudy: String?,

        val photoUrl: String?,

        val personalDescription: ExtendedDescriptionDto?
)