package org.bialydunajec.academicministry.rest.v1

import org.bialydunajec.academicministry.application.command.api.AcademicMinistryCommand
import org.bialydunajec.academicministry.application.command.api.AcademicMinistryCommandGateway
import org.bialydunajec.academicministry.application.dto.toValueObject
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQueryGateway
import org.bialydunajec.academicministry.domain.AcademicMinistryId
import org.bialydunajec.academicministry.domain.valueobject.AcademicPriestSnapshot
import org.bialydunajec.academicministry.rest.v1.request.CreateAcademicMinistryPriestRequest
import org.bialydunajec.academicministry.rest.v1.request.CreateAcademicMinistryRequest
import org.bialydunajec.academicministry.rest.v1.request.UpdateAcademicMinistryRequest
import org.bialydunajec.ddd.application.base.query.api.dto.toValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rest-api/v1/academic-ministry")
internal class AcademicMinistryController(
        private val academicMinistryCommandGateway: AcademicMinistryCommandGateway,
        private val academicMinistryQueryGateway: AcademicMinistryQueryGateway
) {

    //COMMAND----------------------------------------------------------------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAcademicMinistry(@RequestBody request: CreateAcademicMinistryRequest) =
            academicMinistryCommandGateway.process(
                    AcademicMinistryCommand.CreateAcademicMinistry(
                            officialName = request.officialName,
                            shortName = request.shortName,
                            logoImageUrl = request.logoImageUrl?.let { Url(it) },
                            place = request.place?.toValueObject(),
                            socialMedia = request.socialMedia.toValueObject(),
                            emailAddress = request.emailAddress?.let { EmailAddress(it) },
                            photoUrl = request.photoUrl?.let { Url(it) },
                            description = request.description?.toValueObject()
                    )
            )

    @PutMapping("/{academicMinistryId}")
    fun updateAcademicMinistry(@PathVariable academicMinistryId: String, @RequestBody request: UpdateAcademicMinistryRequest) =
            academicMinistryCommandGateway.process(
                    AcademicMinistryCommand.UpdateAcademicMinistry(
                            academicMinistryId = AcademicMinistryId(academicMinistryId),
                            officialName = request.officialName,
                            shortName = request.shortName,
                            logoImageUrl = request.logoImageUrl?.let { Url(it) },
                            place = request.place?.toValueObject(),
                            socialMedia = request.socialMedia.toValueObject(),
                            emailAddress = request.emailAddress?.let { EmailAddress(it) },
                            photoUrl = request.photoUrl?.let { Url(it) },
                            description = request.description?.toValueObject()
                    )
            )

    @PostMapping("/{academicMinistryId}/priest")
    fun createAcademicMinistryPriest(@PathVariable academicMinistryId: String, @RequestBody request: CreateAcademicMinistryPriestRequest) =
            with(request) {
                academicMinistryCommandGateway.process(
                        AcademicMinistryCommand.CreateAcademicMinistryPriest(
                                AcademicMinistryId(academicMinistryId),
                                AcademicPriestSnapshot(
                                        FirstName(firstName),
                                        LastName(lastName),
                                        personalTitle?.toValueObject(),
                                        emailAddress?.let { EmailAddress(it) },
                                        phoneNumber?.let { PhoneNumber(it) },
                                        description?.toValueObject(),
                                        photoUrl?.let { Url(it) }
                                )
                        )
                )
            }


    //QUERY-------------------------------------------------------------------------------------------------------------
    @GetMapping
    fun getAllAcademicMinistries() =
            academicMinistryQueryGateway.process(AcademicMinistryQuery.All())
                    .sortedBy { it.getDisplayName() }


    @GetMapping("/{academicMinistryId}")
    fun getAcademicMinistryById(@PathVariable academicMinistryId: String) =
            academicMinistryQueryGateway.process(AcademicMinistryQuery.ById(academicMinistryId))
}