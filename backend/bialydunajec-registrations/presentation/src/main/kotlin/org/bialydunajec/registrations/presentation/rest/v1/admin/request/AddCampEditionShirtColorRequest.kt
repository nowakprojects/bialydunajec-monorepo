package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import org.bialydunajec.registrations.dto.ColorDto

data class AddCampEditionShirtColorRequest(
        val color: ColorDto,
        val available: Boolean
)