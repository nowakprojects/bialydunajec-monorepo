package org.bialydunajec.academicministry.domain.academicministry

import org.bialydunajec.academicministry.domain.academicministry.entity.AcademicPriest
import org.bialydunajec.academicministry.domain.academicministry.valueobject.AcademicMinistrySnapshot
import org.bialydunajec.academicministry.domain.academicministry.valueobject.SocialMedia
import org.bialydunajec.ddd.domain.base.aggregate.AggregateRoot
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Place
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes.ExtendedDescription
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(schema = "academic_ministry")
internal class AcademicMinistry(
        academicMinistryId: AcademicMinistryId,

        @NotBlank
        private var officialName: String,

        @NotBlank
        private var shortName: String,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "logoImageUrl")))
        private var logoImageUrl: Url? = null,

        @Embedded
        private var place: Place? = null,

        @Embedded
        private var socialMedia: SocialMedia = SocialMedia(),

        @Embedded
        private var emailAddress: EmailAddress? = null,

        @Embedded
        @AttributeOverrides(AttributeOverride(name = "url", column = Column(name = "photoUrl")))
        private var photoUrl: Url? = null,

        @Embedded
        private var description: ExtendedDescription? = null
) : AggregateRoot<AcademicMinistryId, AcademicMinistryEvent>(academicMinistryId) {

    @OneToMany(cascade = [CascadeType.ALL])
    private var priests: List<AcademicPriest> = mutableListOf();

    fun getOfficialName() = officialName
    fun getShortName() = shortName
    fun getLogoImageUrl() = logoImageUrl
    fun getPlace() = place
    fun getSocialMedia() = socialMedia
    fun getEmailAddress() = emailAddress
    fun getPhotoUrl() = photoUrl
    fun getDescription() = description
    fun getPriestsSnapshots() = priests.map { it.getSnapshot() }

    fun getSnapshot() = AcademicMinistrySnapshot(
            academicMinistryId = getAggregateId(),
            officialName = officialName,
            shortName = shortName,
            logoImageUrl = logoImageUrl,
            place = place,
            socialMedia = socialMedia,
            emailAddress = emailAddress,
            photoUrl = photoUrl,
            description = description,
            priests = getPriestsSnapshots()
    )
}