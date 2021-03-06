package org.bialydunajec.campedition.application.query.readmodel

import org.bialydunajec.campedition.application.query.api.CampEditionQuery
import org.bialydunajec.campedition.application.query.api.dto.CampEditionDto
import org.bialydunajec.campedition.domain.campedition.CampEditionId
import org.bialydunajec.campedition.domain.campedition.CampEditionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
internal class CampEditionDomainModelReader(private val campEditionRepository: CampEditionRepository) {

    fun readFor(query: CampEditionQuery.All): Collection<CampEditionDto> =
            campEditionRepository.findAll()
                    .map { CampEditionDto.from(it.getSnapshot()) }

    fun readFor(query: CampEditionQuery.ById): CampEditionDto? =
            campEditionRepository.findById(CampEditionId(query.campEditionId))?.let { CampEditionDto.from(it.getSnapshot()) }

}
