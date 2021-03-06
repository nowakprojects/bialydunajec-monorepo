package org.bialydunajec.registrations.domain.academicministry

import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.registrations.domain.academicministry.valueobject.AcademicMinistrySnapshot
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

/**
 * Academic ministry in Camp Registrations Bounded Context
 */

/*
FIXME: Name changed from AcademicMinistry to CampRegistrationsAcademicMinistry because of:
java.lang.ClassCastException: org.bialydunajec.registrations.domain.academicministry.AcademicMinistry cannot be cast to org.bialydunajec.academicministry.domain.AcademicMinistry
 */
@Entity
@Table(schema = "camp_registrations")
class CampRegistrationsAcademicMinistry(
        academicMinistryId: AcademicMinistryId,
        @NotBlank
        private var officialName: String,
        @NotEmpty
        private var shortName: String?,
        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "logoImageUrl")))
        private var logoImageUrl: Url? = null
) : AggregateRoot<AcademicMinistryId, AcademicMinistryEvent>(academicMinistryId) {

    init{
        registerEvent(AcademicMinistryEvent.AcademicMinistryCreated(getAggregateId(),getSnapshot()))
    }

    fun updateWith(officialName: String,
                   shortName: String?,
                   logoImageUrl: Url?) {
        this.officialName = officialName
        this.shortName = shortName
        this.logoImageUrl = logoImageUrl

        registerEvent(AcademicMinistryEvent.AcademicMinistryUpdated(getAggregateId(),getSnapshot()))
    }


    fun getOfficialName() = officialName
    fun getShortName() = shortName
    fun getLogoImageUrl() = logoImageUrl
    fun getSnapshot() =
            AcademicMinistrySnapshot(
                    academicMinistryId = getAggregateId(),
                    officialName = officialName,
                    shortName = shortName,
                    logoImageUrl = logoImageUrl
            )

}