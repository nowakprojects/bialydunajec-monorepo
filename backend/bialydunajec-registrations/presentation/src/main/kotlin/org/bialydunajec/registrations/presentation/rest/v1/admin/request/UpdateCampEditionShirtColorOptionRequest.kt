package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import org.bialydunajec.registrations.dto.ColorDto

data class UpdateCampEditionShirtColorOptionRequest(
        val color: ColorDto,
        val available: Boolean
)