package org.bialydunajec.registrations.presentation.rest.v1.admin.request

import org.bialydunajec.registrations.application.dto.ShirtSizeDto

data class UpdateCampEditionShirtSizeOptionRequest(
        val size: ShirtSizeDto,
        val available: Boolean
)